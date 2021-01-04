/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respaldos;

import DAO.ComentarioJpaController;
import DAO.IngredienteJpaController;
import DAO.ListaCompraJpaController;
import DAO.RecetaJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Alergeno;
import DTO.AlergenosIngrediente;
import DTO.Comentario;
import DTO.ComentarioPK;
import DTO.Ingrediente;
import DTO.IngredientesReceta;
import DTO.ListaCompra;
import DTO.ListaCompraPK;
import DTO.Receta;
import DTO.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

/**
 *
 * @author CRIS
 */
@ManagedBean
@ViewScoped
public class recetaRespaldo {

    private Receta receta;
    private Comentario comentario;
    private ComentarioPK comentarioPK;

    private EntityManagerFactory emf;
    private RecetaJpaController recetaJPA;
    private ComentarioJpaController comentarioJPA;
    private ListaCompraJpaController listaCompraJPA;
    private IngredienteJpaController ingredienteJPA;
    private UsuarioJpaController usuarioJPA;

    private boolean recetaFav;
    private String[] listaCompra;
    private ArrayList ingredientesReceta = null;
    private String mail;
    private String msj;
    private String cal;

    public recetaRespaldo() {
        emf = Persistence.createEntityManagerFactory("FoodLabPU");
        recetaJPA = new RecetaJpaController(emf);
        comentarioJPA = new ComentarioJpaController(emf);
        listaCompraJPA = new ListaCompraJpaController(emf);
        ingredienteJPA = new IngredienteJpaController(emf);
        usuarioJPA = new UsuarioJpaController(emf);
        receta = new Receta();
        comentario = new Comentario();
        comentarioPK = new ComentarioPK();
        cargaReceta();
        cargaAlergenos();

    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public String getMail() {
        if (usuarioJPA.usuarioConectado() != null) {
            mail = usuarioJPA.usuarioConectado().getCorreo();
        }
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String[] getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(String[] listaCompra) {
        this.listaCompra = listaCompra;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public ComentarioPK getComentarioPK() {
        return comentarioPK;
    }

    public void setComentarioPK(ComentarioPK comentarioPK) {
        this.comentarioPK = comentarioPK;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public boolean isRecetaFav() {
        return recetaFav;
    }

    public void setRecetaFav(boolean recetaFav) {
        this.recetaFav = recetaFav;
    }

    public ArrayList getIngredientesReceta() {
        if (ingredientesReceta == null) {
            ingredientesReceta = new ArrayList();

            List ir = receta.getIngredientesRecetaList();

            for (Object object : ir) {

                IngredientesReceta dato = (IngredientesReceta) object;

                ingredientesReceta.add(new SelectItem(dato.getCantidad() + "," + dato.getUnidadMedida() + "," + dato.getIngrediente1().getNombre(),
                        dato.getCantidad() + " " + dato.getUnidadMedida() + " " + dato.getIngrediente1().getNombre()));

            }
        }
        return ingredientesReceta;
    }

    public void setIngredientesReceta(ArrayList ingredientesReceta) {
        this.ingredientesReceta = ingredientesReceta;
    }

    //******************************************
    public void cargaReceta() {

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession sesion = (HttpSession) ctx.getSession(true);
        inicioRespaldo ir = (inicioRespaldo) sesion.getAttribute("inicioRespaldo");
        receta = recetaJPA.findReceta(ir.getCodigo());
        recetaFav = recetaGuardada();

        List i = receta.getIngredientesRecetaList();
        float c = 0;
        for (Object object : i) {
            IngredientesReceta ire = (IngredientesReceta) object;
            String ctd = ire.getCantidad();
            String[] d = ctd.split("/");
            if (d.length == 1) {
                c += (ire.getIngrediente1().getKcal() / 100) * (Integer.parseInt(ire.getCantidad()));
            }else{
                float valor = Integer.parseInt(d[0])/Integer.parseInt(d[1]);
                 c += (ire.getIngrediente1().getKcal() / 100) * (valor);
            }
        }

        if (c < 100) {
            cal = "Carga calórica baja";
        } else {
            if (100 < c && c < 300) {
                cal = "Carga calórica media";
            } else {
                cal = "Carga calórica alta";
            }
        }

    }

    public ArrayList cargaAlergenos() {

        ArrayList a = new ArrayList();
        List i = receta.getIngredientesRecetaList();

        for (Object o : i) {
            IngredientesReceta ingR = (IngredientesReceta) o;
            Ingrediente ing = ingR.getIngrediente1();
            List ai = ing.getAlergenosIngredienteList();

            for (Object o2 : ai) {
                AlergenosIngrediente alerIng = (AlergenosIngrediente) o2;
                Alergeno al = alerIng.getAlergeno1();

                if (!a.contains(al)) {
                    a.add(al);
                }
            }
        }
        return a;
    }

    public String[] obtenerParrafos() {
        String contenido = receta.getContenido();
        String[] p = contenido.split("\r\n");
        return p;
    }

    public ArrayList obtenerValoracion() {

        ArrayList v = new ArrayList();
        List c = receta.getComentarioList();
        long nValoraciones = 0;
        long puntuacion = 0;

        if (c.isEmpty()) {
            v.add("no hay valoraciones todavía");
            return v;
        }

        for (Object object : c) {
            Comentario coment = (Comentario) object;
            long p = coment.getPuntuacion();
            puntuacion = puntuacion + p;
            nValoraciones++;
        }

        v = calculoEstrellas(puntuacion, nValoraciones);
        return v;
    }

    public ArrayList calculoEstrellas(long p, long n) {

        ArrayList v = new ArrayList();
        int valor = Math.round(p / n);
        for (int i = 0; i < 5; i++) {
            while (valor > 0) {
                if (valor != 0) {
                    if (valor == 1) {
                        v.add(1);
                    } else {
                        v.add(2);
                    }
                } else {
                    v.add(0);
                }
                valor = valor - 2;
            }
        }
        return v;
    }

    public void addComentario() {

        comentario.setReceta(receta);
        comentario.setUsuario1(usuarioJPA.usuarioConectado());
        Date fecha = new Date();
        comentarioPK.setFechaComentario(fecha);
        comentarioPK.setUsuario(usuarioJPA.usuarioConectado().getNick());
        comentarioPK.setCodReceta(receta.getCodReceta());
        comentario.setComentarioPK(comentarioPK);

        try {
            comentarioJPA.create(comentario);
            msj = "Comentario enviado";
        } catch (Exception ex) {
            Logger.getLogger(recetaRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            msj = "Su comentario no se ha podido enviar.";
        }

    }

    public void guardarReceta() {

        Usuario usu = usuarioJPA.usuarioConectado();
        List ususFav = receta.getUsuarioList();
        ususFav.add(usu);
        receta.setUsuarioList(ususFav);

        try {
            recetaJPA.edit(receta);
            recetaFav = true;
            msj = "Receta guardada correctamente.";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(recetaRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(recetaRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void guardarReceta2() {

        Usuario usu = usuarioJPA.usuarioConectado();
        List ususFav = receta.getUsuarioList();
        ususFav.remove(usu);
        receta.setUsuarioList(ususFav);

        try {
            recetaJPA.edit(receta);
            recetaFav = false;
            msj = "La receta se eliminó de favoritos correctamente.";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(recetaRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(recetaRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean recetaGuardada() {
        Usuario usu = usuarioJPA.usuarioConectado();
        List ususFav = receta.getUsuarioList();
        return ususFav.contains(usu);
    }

    public void addLista() {

        Usuario usu = usuarioJPA.usuarioConectado();
        List l = usu.getListaCompraList();

        for (int i = 0; i < listaCompra.length; i++) {
            String[] datos = listaCompra[i].split(",");
            Ingrediente ing = ingredienteJPA.ingPorNombre(datos[2]).get(0);
            ListaCompra lc = new ListaCompra(new ListaCompraPK(usu.getNick(), ing.getCodIngrediente()));

            if (l.contains(lc)) {
                ListaCompra lcUsu = listaCompraJPA.findListaCompra(lc.getListaCompraPK());
                String ctd = lcUsu.getCantidad();
                lcUsu.setCantidad(ctd + datos[0]);
                try {
                    listaCompraJPA.edit(lcUsu);
                } catch (Exception ex) {
                    Logger.getLogger(recetaRespaldo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                lc.setCantidad(datos[0]);
                lc.setUnidad(datos[1]);
                lc.setUsuario1(usu);
                lc.setIngrediente1(ing);
                try {
                    listaCompraJPA.create(lc);
                    msj = "Añadido a la lista correctamente.";
                } catch (Exception ex) {
                    Logger.getLogger(recetaRespaldo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public void mail() {

        String de = "prueba2daw@gmail.com";
        String clave = "Prueba2daw.1";
        String para = mail;
        String text = receta.getContenido();
        String archivo = receta.getFoto();
        String asunto = "Receta -" + receta.getNombre();
        String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/img/recetas");
        try {

            Properties p = new Properties();

            p.setProperty("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", de);
            p.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(p);

            BodyPart texto = new MimeBodyPart();
            texto.setText(text);

            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(ruta + "/" + archivo)));
            adjunto.setFileName(archivo);

            MimeMultipart parteComp = new MimeMultipart();
            parteComp.addBodyPart(texto);
            parteComp.addBodyPart(adjunto);

            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(de);
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
            mensaje.setSubject(asunto);
            mensaje.setContent(parteComp);

            Transport t = session.getTransport("smtp");
            t.connect(de, clave);
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            msj = "Correo mandado con éxito.";
        } catch (MessagingException ex) {
            Logger.getLogger(recetaRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            msj = "No se ha podido mandar el correo.";
        }

    }

    public void pdf() {
        GeneratePDFFileIText pdf = new GeneratePDFFileIText();
        msj = pdf.createPDF(receta);
    }

}
