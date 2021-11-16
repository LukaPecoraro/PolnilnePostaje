package si.fri.prpo.projektPolnilnePostaje.api;

import si.fri.prpo.projektPolnilnePostaje.entitete.Uporabnik;
import si.fri.prpo.projektPolnilnePostaje.zrna.UporabnikZrno;
import si.fri.prpo.projektPolnilnePostaje.zrna.UpravljanjePolnilnicZrno;
import si.fri.prpo.projektPolnilnePostaje.zrna.UpravljanjeRezervacijZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class Servlet extends HttpServlet {

    @Inject
    private UporabnikZrno uporabnikiZrno;

    @Inject
    private UpravljanjePolnilnicZrno upz;
    @Inject
    private UpravljanjeRezervacijZrno urz;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Uporabnik> uporabniki = uporabnikiZrno.getUporabniki();

        PrintWriter mojPrinter = resp.getWriter();
        for (Uporabnik uporabnik : uporabniki) {
            System.out.println(uporabnik);
            mojPrinter.println(uporabnik);
        }

        //se z criteriaAPI
        List<Uporabnik> uporabnikiCriteria = uporabnikiZrno.getUporabnikiCriteriaAPI();
        for (Uporabnik uporabnik : uporabnikiCriteria) {
            mojPrinter.println("Criteria API: " + uporabnik);
        }

        mojPrinter.printf("UpravljanjePolnilnicZrno ima UUID ob 1. klicu: %s\n", upz.izpisiUUID().toString());
        mojPrinter.printf("UpravljanjePolnilnicZrno ima UUID ob 2. klicu: %s\n", upz.izpisiUUID().toString());

        mojPrinter.printf("UpravljanjeRezervacijZrno ima UUID ob 1. klicu: %s\n", urz.izpisiUUID().toString());
        mojPrinter.printf("UpravljanjeRezervacijZrno ima UUID ob 2. klicu: %s\n", urz.izpisiUUID().toString());
    }

}
