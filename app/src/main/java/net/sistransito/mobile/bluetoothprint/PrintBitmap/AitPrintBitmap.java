package net.sistransito.mobile.bluetoothprint.PrintBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;

import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.bluetoothprint.PrintBitmap.base.BasePrintBitmap;
import net.sistransito.mobile.bluetoothprint.PrintBitmap.base.PrintBitmapFormat;
import net.sistransito.R;

public class AitPrintBitmap extends BasePrintBitmap {
    private AitData aData;
    private net.sistransito.mobile.aitcompany.pjData pjData;
    private String copy;

    public AitPrintBitmap(Context context, AitData autoData) {
        super(context);
        this.aData = autoData;
    }

    public AitPrintBitmap(Context context, AitData autoData, String copy) {
        super(context);
        this.aData = autoData;
        this.copy = copy;
    }

    public AitPrintBitmap(Context context, net.sistransito.mobile.aitcompany.pjData autoData, String copy) {
        super(context);
        this.pjData = autoData;
        this.copy = copy;
    }

    @Override
    public Bitmap getBitmap() {

        String descriptionText, documentTitleDescription, titleStateIdentity, fieldTitle, fieldText, tipoAbordagem, docProcedimentos;

        PrintBitmapFormat bitmapFormat = new PrintBitmapFormat(context);
        String title = "NOME DO ESTADO\n" +
                "NOME DA SECRETARIA\n" +
                "DEPARTAMENTO DE TRÂNSITO";
        String subTitle = "AUTO DE INFRAÇÃO DE TRÂNSITO (AIT)";

        bitmapFormat.createTitle(title, subTitle, "ic_left_title.png", "ic_right_title.png", PrintBitmapFormat.TITLE_FONT_SIZE, PrintBitmapFormat.TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("1-IDENTIFICAÇÃO DA AUTUAÇÃO", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);

        /*bitmapFormat.createTitle("ÓRGÃO AUTUADOR", user.getCodigoOrgao(), "NÚMERO DO AIT", aData.getNumeroAuto(), PrintBitmapFormat.NORMAL_FONT,
               PrintBitmapFormat.MAIOR_FONT);*/

        bitmapFormat.createNameTable("ÓRGÃO AUTUADOR", "114100", "NÚMERO DO AIT", aData.getAitNumber(),
                true, PrintBitmapFormat.TableCellAlign.MIDDLE,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createQuotes("2-IDENTIFICAÇÃO DO VEÍCULO", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);

        if (aData.getCountry() == null) {
            bitmapFormat.createTable("PLACA", aData.getPlate(), "UF",
                    aData.getStateVehicle(), "CHASSI", aData.getChassi(), false,
                    PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        }else{
            bitmapFormat.createTable("PLACA", aData.getPlate(), "UF",
                    aData.getStateVehicle(), "PAIS", "BR",
                    "CHASSI", aData.getChassi(), false,
                    PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        }

        bitmapFormat.createTable("MARCA/MODELO", aData.getVehicleModel(),
                "ESPÉCIE", aData.getVehicleSpecies(),
                true, PrintBitmapFormat.TableCellAlign.MIDDLE,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createQuotes("3-IDENTIFICAÇÃO DO CONDUTOR", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("NOME", aData.getConductorName(), true, false, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        if (aData.getDocumentType().contains("CPF") || aData.getDocumentType().contains("RG") || aData.getDocumentType().contains("OUTROS")) {
            documentTitleDescription = aData.getDocumentType();
            titleStateIdentity = null;
        } else {
            documentTitleDescription = "RG/CPF/OUTROS";
            titleStateIdentity = aData.getCnhState();
        }
        bitmapFormat.createTable("REGISTRO CNH/PPD/ACC", aData.getCnhPpd(), "UF", aData.getCnhState(), documentTitleDescription, aData.getDocumentNumber(), true,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createQuotes("4-IDENTIFICAÇÃO DO LOCAL, DATA E HORA", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("LOCAL", aData.getAddress(), true, false, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        bitmapFormat.createNameTable("CÓD. MUNICÍPIO", aData.getCityCode(), "MUNICÍPIO/UF", aData.getCity() + "/" + aData.getState(), true, PrintBitmapFormat.TableCellAlign.MIDDLE, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        bitmapFormat.createNameTable("DATA", aData.getAitDate(), "HORA", aData.getAitTime(), true, PrintBitmapFormat.TableCellAlign.MIDDLE, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        bitmapFormat.createQuotes("5-TIPIFICAÇÃO DA INFRAÇÃO", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createTable("CÓD. INFRAÇÃO", aData.getFramingCode(),
                "DESDOB.", aData.getUnfolding(),
                "AMPARO LEGAL", "Art." + aData.getArticle() + " da Lei 9.503/97", true, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        bitmapFormat.createQuotes("DESCRIÇÃO DA INFRAÇÃO", aData.getInfraction(), true, false, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        if (aData.getDescription().isEmpty()) {
            bitmapFormat.createQuotes("NÚMERO DO TCA", aData.getTcaNumber(), true, false, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        } else {
            bitmapFormat.createQuotes("EQUIPAMENTOS/INSTRUMENTOS DE AFERIÇÃO UTILIZADO", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);

            bitmapFormat.createTable("DESCRIÇÃO", aData.getDescription(), "MARCA", aData.getEquipmentBrand(), true, PrintBitmapFormat.TableCellAlign.MIDDLE, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

            bitmapFormat.createTable("MODELO", aData.getEquipmentModel(), "NÚMERO DE SÉRIE", aData.getSerialNumber(), true, PrintBitmapFormat.TableCellAlign.MIDDLE, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

            bitmapFormat.createTable("MEDIÇÃO REALIZADA", aData.getMeasurementPerformed(), "LIMITE REGULAMENTADO", aData.getRegulatedValue(),
                    true, PrintBitmapFormat.TableCellAlign.MIDDLE,
                    PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

            bitmapFormat.createTable("VALOR CONSIDERADO", aData.getValueConsidered(), "NÚMERO DA AMOSTRA", aData.getAlcoholTestNumber(),
                    true, PrintBitmapFormat.TableCellAlign.MIDDLE,
                    PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);
        }

        if(aData.getProcedures().isEmpty()){
            docProcedimentos = aData.getRetreat();
        } else if(aData.getRetreat().isEmpty()){
            docProcedimentos = aData.getProcedures();
        }else{
            docProcedimentos = aData.getProcedures() + ", " + aData.getRetreat();
        }

        bitmapFormat.createQuotes("PROCEDIMENTOS", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("MEDIDAS ADMINISTRATIVAS", docProcedimentos, true, false, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        if(aData.getApproach().equals("0")){
            tipoAbordagem = context.getString(R.string.sem_abordagem);
        }else{
            tipoAbordagem = context.getString(R.string.com_abordagem);
        }

        bitmapFormat.createObservationQuotes("OBSERVAÇÕES", aData.getObservation(), true, PrintBitmapFormat.NORMAL_FONT);

        bitmapFormat.createQuotes("ABORDAGEM", tipoAbordagem, true, false, PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createQuotes("6-IDENTIFICAÇÃO DA AUTORIDADE OU AGENTE AUTUADOR", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createNameTable("NOME", user.getEmployeeName().toUpperCase(), "MATRÍCULA", user.getRegistration(), false, PrintBitmapFormat.TableCellAlign.RIGHT,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createSignatureQuotes("ASSINATURA", "\n\n\n", true, PrintBitmapFormat.NORMAL_FONT);

        if (aData.getCpfShipper() == null){
            fieldTitle = "CNPJ";
            fieldText = aData.getCnpShipper();
        } else {
            fieldTitle = "CPF";
            fieldText = aData.getCpfShipper();
        }

        bitmapFormat.createQuotes("7-IDENTIFICAÇÃO DO EMBARCADOR OU EXPEDIDOR", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createNameTable("NOME", aData.getShipperIdentification(), fieldTitle, fieldText, true, PrintBitmapFormat.TableCellAlign.RIGHT,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        if (aData.getCpfCarrier() == null){
            fieldTitle = "CNPJ";
            fieldText = aData.getCnpjCarrier();
        } else {
            fieldTitle = "CPF";
            fieldText = aData.getCpfCarrier();
        }

        bitmapFormat.createQuotes("8-IDENTIFICAÇÃO DO TRANSPORTADOR", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createNameTable("NOME", aData.getCarrierIdentification(), fieldTitle, fieldText, true, PrintBitmapFormat.TableCellAlign.RIGHT,
                PrintBitmapFormat.NORMAL_FONT, PrintBitmapFormat.MEDIO_FONT);

        bitmapFormat.createQuotes("9-ASSINATURA DO INFRATOR OU CONDUTOR", Paint.Align.LEFT, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createSignatureQuotes("\n\n\n", "\n\n\n", true, PrintBitmapFormat.NORMAL_FONT);

        bitmapFormat.setNewLine(1);
        bitmapFormat.createQuotes("VIA DO " + copy, Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.setNewLine(2);

        /*bitmapFormat.createQuotes("ORIENTAÇÕES PARA APRESENTAÇÃO", Paint.Align.CENTER, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("DE DEFESA DE AUTUAÇÃO", Paint.Align.CENTER, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.setNewLine(2);
        bitmapFormat.createQuotes("O Auto de Infração de Trânsito - AIT valerá como NOTIFICAÇÃO DE AUTUAÇÃO quando assinado pelo condutor quando:", Paint.Align.LEFT, true, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.createQuotes("I- A infração for de responsabilidade do condutor;", Paint.Align.LEFT, true, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.createQuotes("II- A infração for de responsabilidade do proprietário e este estiver conduzindo o veículo.", Paint.Align.LEFT, true, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.createQuotes("a) Nos casos acima o prazo para a apresentação da DEFESA DE AUTUAÇÃO é de 15 (quinze) dias, " +
                "contados a partir da data da assinatura do auto de infração;).", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.setNewLine(3);
        bitmapFormat.createQuotes("b) Nos demais casos, o prazo para a apresentação da DEFESA DE AUTUAÇÃO é " +
                "de 15 (quinze) dias contados da data da Notificação da Autuação. O ÓRGÃO disponibizará, em seus postos " +
                "de atendimento e através da internet no site: www.orgao.transito.com.br, modelo_veiculo padrão de REQUERIMENTO para apresentação " +
                "de DEFESA DE AUTUAÇÃO;", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.setNewLine(3);
        bitmapFormat.createQuotes("c) Elebore sua DEFESA DE AUTUAÇÃO expondo os motivos, alegações e argumentos permitidos em lei;", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.setNewLine(3);
        bitmapFormat.createQuotes("d) Documentação (cópia) a ser anexada na DEFESA DE AUTUAÇÃO:", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.setNewLine(2);
        bitmapFormat.createQuotes("- Auto de Infração de Transito - AIT ou Notificação de Autuação - NA;", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.createQuotes("- Certificado de Registro e Licenciamento de Veículo - CRLV;", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.createQuotes("- Documento de Identificação, com foto e assinatura;", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.createQuotes("- CPF, caso não conste no documento de identificação;", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.createQuotes("- Procuração, com firma reconhecida, quando se fizer representar por terceiros;", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.createQuotes("- Se possoa Jurídica: Contrato Social e Procuração com firma reconhecida quando o resposável legal se fizer representar por terceiros;", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.createQuotes("- Provas documentais previstas em lei que comprovem as alegações apresentadas.", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);

        bitmapFormat.setNewLine(3);
        bitmapFormat.createQuotes("e) O requerimento com a DEFESA DE AUTUAÇÃO poderá ser entregue no protocolo da sede do " +
                "Orgão, Postos de Atendimento ou remetido via postal para:", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.setNewLine(3);
        bitmapFormat.createQuotes("Departamento de Trânsito do Estado", Paint.Align.CENTER, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("Endereço compleot", Paint.Align.CENTER, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);
        bitmapFormat.createQuotes("CEP: 00000-000, Cidade-UF", Paint.Align.CENTER, true, false, PrintBitmapFormat.SUB_TITLE_FONT_SIZE);

        bitmapFormat.setNewLine(3);

        bitmapFormat.createQuotes("f) Apresentada a DEFESA DE AUTUAÇÃO, no prezo previsto e desde que preenchidos " +
                "os requisitos legais, o ÓRGÃO apreciará e caso considere procedente, cancelará o Auto de Infração de Transito - AIT " +
                "e comunicará o fato ao proprietário do veículo;", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.setNewLine(3);
        bitmapFormat.createQuotes("g) Em caso de não apresentação de DEFESA DE AUTUAÇÃO, do seu não conhecimento, ou ainda, " +
                "de indeferimento, o ÓRGÃO aplicará a penalidade expedindo a respectiva NOTIFICAÇÃO DE PENALIDADE; " +
                "pena de invalidade da penalidade aplicada.", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
        bitmapFormat.setNewLine(3);
        bitmapFormat.createQuotes("h) É obrigatório a presença do código RENAINF ou INFRAEST na " +
                "notificação de penalidade, nos casos de infrações vinculadas ao veículo, sob " +
                "pena de invalidade da penalidade aplicada.", Paint.Align.LEFT, false, false, PrintBitmapFormat.NORMAL_FONT);
*/

        bitmapFormat.setNewLine(25);
        bitmapFormat.printDocumentClose();
        bitmapFormat.saveBitmap();

        return bitmapFormat.getPrintBitmap();

    }
}

