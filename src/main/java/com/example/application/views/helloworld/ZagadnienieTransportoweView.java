package com.example.application.views.helloworld;

import com.example.application.modules.Dostawca;
import com.example.application.modules.Odbiorca;
import com.example.application.modules.ZyskAndResult;
import com.example.application.solver.Solver;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;

import java.util.LinkedList;
import java.util.List;

@Route(value = "hello", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Zagadnienie Transportowe")
@CssImport("./views/helloworld/hello-world-view.css")
public class ZagadnienieTransportoweView extends HorizontalLayout {

    private NumberField prod1P;
    private NumberField prod2P;
    private NumberField prodFictionalP;

    private NumberField odb1P;
    private NumberField odb2P;
    private NumberField odbFictionalP;

    private Text transportDescription;

    private NumberField transport11; private NumberField transport12; private NumberField transport13;
    private NumberField transport21; private NumberField transport22; private NumberField transport23;
    private NumberField transport31; private NumberField transport32; private NumberField transport33;

    private NumberField prod1Price;
    private NumberField prod2Price;
    private NumberField prodFictionalPrice;

    private NumberField odb1Price;
    private NumberField odb2Price;
    private NumberField odbFictionalPrice;

    private Button Solve;


    public ZagadnienieTransportoweView() {
        addClassName("hello-world-view");
        prod1P = new NumberField("Podaż 1");
        prod2P = new NumberField("Podaż 2");
        prodFictionalP = new NumberField("Podaż fikcyjna");

        odb1P = new NumberField("Popyt 1");
        odb2P = new NumberField("Popyt 2");
        odbFictionalP = new NumberField("Popyt fikcyjny");

        transport11 = new NumberField();   transport12 = new NumberField();   transport13 = new NumberField();
        transport21 = new NumberField();   transport22 = new NumberField();   transport23 = new NumberField();
        transport31 = new NumberField();   transport32 = new NumberField();   transport33 = new NumberField();

        Solve = new Button("Solve");

        HorizontalLayout prodLayout = new HorizontalLayout(prod1P, prod2P, prodFictionalP);
        prodLayout.setAlignItems(Alignment.CENTER);
        add(prodLayout);
        setVerticalComponentAlignment(Alignment.END, prod1P, prodFictionalP);

        HorizontalLayout odbLayout = new HorizontalLayout(odb1P, odb2P, odbFictionalP);
        odbLayout.setAlignItems(Alignment.CENTER);
        add(odbLayout);

        transportDescription = new Text("Ceny transportu");
        HorizontalLayout transportDescriptionLayout = new HorizontalLayout(transportDescription);
        transportDescriptionLayout.setAlignItems(Alignment.BASELINE);
        add(transportDescriptionLayout);

        HorizontalLayout transportLayout1 = new HorizontalLayout(transport11, transport12, transport13);
        HorizontalLayout transportLayout2 = new HorizontalLayout(transport21, transport22, transport23);
        HorizontalLayout transportLayout3 = new HorizontalLayout(transport31, transport32, transport33);

        transportLayout1.setAlignItems(Alignment.CENTER);
        transportLayout2.setAlignItems(Alignment.CENTER);
        transportLayout3.setAlignItems(Alignment.CENTER);

        add(transportLayout1);
        add(transportLayout2);
        add(transportLayout3);

        prod1Price = new NumberField("Cena zakupu 1");
        prod2Price = new NumberField("Cena zakupu 2");
        prodFictionalPrice = new NumberField("Cena zakupu Fikcyjnego");;
        HorizontalLayout prodPrices = new HorizontalLayout(prod1Price, prod2Price, prodFictionalPrice);
        add(prodPrices);

        odb1Price = new NumberField("Cena sprzedaży 1");
        odb2Price = new NumberField("Cena sprzedaży 2");
        odbFictionalPrice = new NumberField("Cena sprzedaży fikcyjnej");
        HorizontalLayout odbPrices = new HorizontalLayout(odb1Price, odb2Price, odbFictionalPrice);
        add(odbPrices);

        HorizontalLayout solveLayout = new HorizontalLayout(Solve);
        odbLayout.setAlignItems(Alignment.CENTER);
        add(solveLayout);
        Solve.addClickListener(e -> {
            List<Dostawca> dostawcy = new LinkedList<>();
            List<Odbiorca> odbiorcy = new LinkedList<>();
            double[][] transport = new double[3][3];

            transport [0][0] = transport11.getValue(); transport[0][1] = transport12.getValue(); transport[0][2] = 999999999999999.0;
            transport [1][0] = transport21.getValue(); transport[1][1] = transport22.getValue(); transport[1][2] = 999999999999999.0;
            transport [2][0] = 999999999999999.0;      transport[2][1] = 999999999999999.0;      transport[2][2] = 999999999999999.0;

            dostawcy.add(new Dostawca(prod1Price.getValue(), prod1P.getValue().intValue()));
            dostawcy.add(new Dostawca(prod2Price.getValue(), prod2P.getValue().intValue()));
            dostawcy.add(new Dostawca(prodFictionalPrice.getValue(), prodFictionalP.getValue().intValue()));

            odbiorcy.add(new Odbiorca(odb1Price.getValue(),odb1P.getValue().intValue()));
            odbiorcy.add(new Odbiorca(odb2Price.getValue(),odb1P.getValue().intValue()));
            odbiorcy.add(new Odbiorca(odbFictionalPrice.getValue(), odbFictionalP.getValue().intValue()));

            ZyskAndResult[][] zysk = new ZyskAndResult[transport.length][transport[0].length];
            zysk = Solver.solve(dostawcy, odbiorcy, transport, zysk,true);

            Dialog result = new Dialog();
            result.add(new Text("Result"));


            NumberField res11 = new NumberField(); res11.setValue((double) zysk[0][0].getAmountValue()); res11.setReadOnly(true);
            NumberField res12 = new NumberField(); res12.setValue((double) zysk[0][1].getAmountValue()); res12.setReadOnly(true);
            NumberField res13 = new NumberField(); res13.setValue((double) zysk[0][2].getAmountValue()); res13.setReadOnly(true);
            HorizontalLayout layout1 = new HorizontalLayout(res11, res12, res13);

            NumberField res21 = new NumberField(); res21.setValue((double) zysk[1][0].getAmountValue()); res21.setReadOnly(true);
            NumberField res22 = new NumberField(); res22.setValue((double) zysk[1][1].getAmountValue()); res22.setReadOnly(true);
            NumberField res23 = new NumberField(); res23.setValue((double) zysk[1][2].getAmountValue()); res23.setReadOnly(true);
            HorizontalLayout layout2 = new HorizontalLayout(res21, res22, res23);

            NumberField res31 = new NumberField(); res31.setValue((double) zysk[2][0].getAmountValue()); res31.setReadOnly(true);
            NumberField res32 = new NumberField(); res32.setValue((double) zysk[2][1].getAmountValue()); res32.setReadOnly(true);
            NumberField res33 = new NumberField(); res33.setValue((double) zysk[2][2].getAmountValue()); res33.setReadOnly(true);
            HorizontalLayout layout3 = new HorizontalLayout(res31, res32, res33);

            result.add(layout1);
            result.add(layout2);
            result.add(layout3);

            result.setCloseOnOutsideClick(true);
            result.open();
        });
    }

}
