package net.sistransito.mobile.bluetoothprint.PrintBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;

import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.bluetoothprint.PrintBitmap.base.BasePrintBitmap;
import net.sistransito.mobile.bluetoothprint.PrintBitmap.base.PrintBitmapFormat;
import net.sistransito.mobile.tav.TavData;
import net.sistransito.mobile.util.User;

/**
 * Created by GAZI_RIMON on 8/15/2016.
 */
public class TavPrintBitmap extends BasePrintBitmap {
    private AitData aitData;
    private TavData tavData;
    private User userData;

    public TavPrintBitmap(Context context, TavData tavData, AitData aitData) {
        super(context);
        this.aitData =aitData;
        this.tavData=tavData;
    }

    @Override
    public Bitmap getBitmap() {
        PrintBitmapFormat bitmapFormat = new PrintBitmapFormat(context);

        String textoTituloDoc;

        String sStructure = "Cabeça de alavanca - " + tavData.getLeverHead() + "\n" +
                "Carroceria - " + tavData.getCarBody() + "\n" +
                "Forro - " + tavData.getLining() + "\n" +
                "Lataria capô - " + tavData.getHoodBody() + "\n" +
                "Lataria lado direito - " + tavData.getRightSideBody() + "\n" +
                "Lataria lado esquerdo - " + tavData.getLeftSideBody() + "\n" +
                "Tampa porta mala - " + tavData.getTrunkBodywork() + "\n" +
                "Lataria teto - " + tavData.getRoofBodywork() + "\n" +
                "Motor - " + tavData.getEnginer() + "\n" +
                "Painel - " + tavData.getDashboard() + "\n" +
                "Pintura capô - " + tavData.getHoodPaint() + "\n" +
                "Pintura lado direito - " + tavData.getRightSidePaint() + "\n" +
                "Pintura lado esquerdo - " + tavData.getLeftSidePaint() + "\n" +
                "Pintura porta mala - " + tavData.getTrunkPainting() + "\n" +
                "Pintura teto - " + tavData.getCeilingPainting() + "\n" +
                "Radiador - " + tavData.getRadiator() + "\n" +
                "Vidros laterais - " + tavData.getSideWindows() + "\n" +
                "Vidro para-brisa - " + tavData.getWindShieldGlass();


        String sAccessories = "Antena de rádio - " + tavData.getAntenna() + "\n" +
                "Bagageiro - " + tavData.getTrunk() + "\n" +
                "Bancos - " + tavData.getSeats() + "\n" +
                "Bateria - " + tavData.getBaterry() + "\n" +
                "Calota - " + tavData.getWheelCover() + "\n" +
                "Condicionador de ar - " + tavData.getAirConditioner() + "\n" +
                "Extintor de incêndio - " + tavData.getFireExtinguisher() + "\n" +
                "Farolete dianteiro - " + tavData.getHeadLight() + "\n" +
                "Farolete traseiro - " + tavData.getTaiLight() + "\n" +
                "Macaco - " + tavData.getJack() + "\n" +
                "Pára-choque dianteiro - " + tavData.getFrontBumper() + "\n" +
                "Pára-choque traseiro - " + tavData.getBackBumper() + "\n" +
                "Pára-sol condutor - " + tavData.getDriverSunVisor() + "\n" +
                "Pneus - " + tavData.getTires() + "\n" +
                "Estepe - " + tavData.getSpareTire() + "\n" +
                "Rádio - " + tavData.getRadio() + "\n" +
                "Retrovisor interno - " + tavData.getRearviewMirror() + "\n" +
                "Retrovisor externo direito - " + tavData.getRightSideMirror() + "\n" +
                "Tapete - " + tavData.getCarpet() + "\n" +
                "Triângulo - " + tavData.getTriangle() + "\n" +
                "Volante - " + tavData.getSteeringWheel();

        String sTextCaptions = "AM - Amongado | OK - Funcionando | TR - Trincado\n" +
                "NT - Não tem | IN - Inoperante | TB - Desbotado\n" +
                "QB - Quebrado | RC - Riscado | RG - Rasgado";

        String title = "NOME DO ESTADO\n" +
                "NOME DA SECRETARIA\n" +
                "DEPARTAMENTO DE TRÂNSITO";
        String subTitle = "TERMO DE APREENSÃO DE VEÍCULO (TAV)";

        bitmapFormat.createTitle(title, subTitle, "ic_left_title.png", "ic_right_title.png", PrintBitmapFormat.TITLE_FONT_SIZE, PrintBitmapFormat.TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("NUMERAÇÃO DO TAV", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createNameTable("NÚMERO DO TAV", tavData.getTavNumber(), "NÚMERO DO AIT", aitData.getAitNumber(), true, PrintBitmapFormat.TableCellAlign.MIDDLE,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        bitmapFormat.createQuotes("MOTIVO PARA O RECOLHIMENTO", aitData.getArticle().toUpperCase(), true, false, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createQuotes("IDENTIFICAÇÃO DO LOCAL, DATA E HORA", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("LOCAL", aitData.getAddress(), true, false, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        bitmapFormat.createTable("DATA", aitData.getAitDate(), "HORA", aitData.getAitTime(), "MUNICÍPIO/UF", aitData.getCity() + "/" + aitData.getState(),
                true, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createQuotes("IDENTIFICAÇÃO DO CONDUTOR", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("NOME", aitData.getConductorName(), true, false, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        if (aitData.getDocumentType().contains("CPF") || aitData.getDocumentType().contains("RG") || aitData.getDocumentType().contains("OUTROS")) {
            textoTituloDoc = aitData.getDocumentType();
        } else {
            textoTituloDoc = "RG/CPF/OUTROS";
        }
        bitmapFormat.createTable("REGISTRO CNH/PPD/ACC", aitData.getCnhPpd(), "UF", aitData.getCnhState(), textoTituloDoc, aitData.getDocumentNumber(),
                true, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createQuotes("IDENTIFICAÇÃO DO VEÍCULO", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createTable("PLACA", aitData.getPlate(), "UF", aitData.getState(), "PAIS", aitData.getCountry(),
                true, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        bitmapFormat.createTable("CHASSI", aitData.getChassi(), "RENAVAN", tavData.getRenavamNumber(), true, PrintBitmapFormat.TableCellAlign.MIDDLE,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        bitmapFormat.createTable("MARCA/MODELO", aitData.getVehicleModel(), "ESPÉCIE", aitData.getVehicleSpecies(), true, PrintBitmapFormat.TableCellAlign.MIDDLE,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createQuotes("CONDIÇÕES DO VEÍCULO NO ATO DA REMOÇÃO", Paint.Align.CENTER, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);


        bitmapFormat.createStructureTable("QUANTO À ESTRUTURA", sStructure, "QUANTO AOS ACESSÓRIOS", sAccessories,
                true, PrintBitmapFormat.TableCellAlign.MIDDLE, PrintBitmapFormat.SUB_TITLE_FONT_SIZE, PrintBitmapFormat.NORMAL_FONT);

        bitmapFormat.createTextCaptions("LEGENDAS", sTextCaptions,  PrintBitmapFormat.NORMAL_FONT);
        //bitmapFormat.createQuotes("LEGENDAS", sTextCaptions, true, false, PrintBitmapFormat.MEDIO_FONT, PrintBitmapFormat.NORMAL_FONT);

        bitmapFormat.createTable("ODÔMETRO", tavData.getOdometer(), "COMBUSTÍVEL", tavData.getFuelGauge(),
                true, PrintBitmapFormat.TableCellAlign.MIDDLE,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createTable("PARQUE DE RETENÇÃO", "DETRAN", "REMOVIDO ATRAVÉS DE", tavData.getRemovedVia(), true, PrintBitmapFormat.TableCellAlign.MIDDLE,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        if (tavData.getRemovedVia().contains("Guincho")){
            bitmapFormat.createTable("EMPRESA", tavData.getCompanyName(), "CONDUTOR DO GUINCHO", tavData.getWinchDriverName(), true, PrintBitmapFormat.TableCellAlign.LEFT,
                    PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
            bitmapFormat.createSignatureQuotes("ASSINATURA DO CONDUTOR DO GUINCHO", "\n\n\n", true, PrintBitmapFormat.NORMAL_FONT);
        } else {
            bitmapFormat.createNameTable("NOME", user.getEmployeeName().toUpperCase(), "MATRÍCULA", user.getRegistration(), true, PrintBitmapFormat.TableCellAlign.LEFT,
                    PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        }

        bitmapFormat.createObservationQuotes("OBSERVAÇÕES", tavData.getObservation(), true, PrintBitmapFormat.NORMAL_FONT);

        bitmapFormat.createQuotes("IDENTIFICAÇÃO DA AUTORIDADE OU AGENTE AUTUADOR", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createNameTable("NOME", user.getEmployeeName().toUpperCase(), "MATRÍCULA", user.getRegistration(), true, PrintBitmapFormat.TableCellAlign.RIGHT
                , PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        bitmapFormat.createSignatureQuotes("ASSINATURA", "\n\n\n", true, PrintBitmapFormat.NORMAL_FONT);

        bitmapFormat.setNewLine(3);

        bitmapFormat.createQuotes("Pátio de Retenção do Orgao", Paint.Align.CENTER, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("Endereço completo", Paint.Align.CENTER, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("CEP: 00000-000, Cidade-UF", Paint.Align.CENTER, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);

        bitmapFormat.setNewLine(25);
        bitmapFormat.printDocumentClose();
        bitmapFormat.saveBitmap();
        return bitmapFormat.getPrintBitmap();
    }
}

