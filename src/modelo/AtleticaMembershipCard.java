package modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class AtleticaMembershipCard
{
    private BufferedImage image, blankID;
    private Graphics2D g2d;
    private Font font;
    private Color fontColor;
    private String fontName;
    private FontMetrics fontMetrics;
    private int fontSize = 12;
    
    private static final int NAME_X = 65;
    private static final int NAME_Y = 328;
    private static final int BDAY_X = 65;
    private static final int BDAY_Y = 365;
    private static final int SEX_X = 257;
    private static final int SEX_Y = 365;
    private static final int CPF_X = 65;
    private static final int CPF_Y = 401;
    private static final int RH_X = 255;
    private static final int RH_Y = 401;
    private static final int MED_X = 101;
    private static final int MED_Y = 432;
    private static final int ASSOC_X = 82;
    private static final int ASSOC_Y = 504;
    private static final int ALL_DIS_MED_X_MAX = 275;

    /**
     * Desenha as informações de um Aluno nos campos da carteirinha e salva a imagem resultante como PDF
     * @param student Aluno de onde as informações a serem desenhadas são extraídas
     */
    public void generateID(Aluno student)
    {
        //Inicializa a imagem da carteirinha e o Graphics2D que trabalhará em cima desta
        initialize();

        //Definindo a string que representa o sexo do Aluno
        String studentSex;
        
        if(student.getSexo() == 1)
        {
            studentSex = "F";
        }
        else
        {
            studentSex = "M";
        }
        
        //Definindo a string que representa o Vínculo do aluno
        String studentAssociation;
        
        switch(student.getVinculo())
        {
            case 0:
                studentAssociation = "Associado";
                break;
            case 1:
                studentAssociation = "Atleta";
                break;
            default:
                studentAssociation = "Atleta Associado";
                break;
        }
        
        //Gera uma string de [11-QTD_DE_DIGITOS_NO_CPF] zeros concatenada o CPF e então adiciona os pontos e traço ao CPF formatado
        String studentCPFNoFormat = new String(new char[11-(((int)Math.floor(Math.log10(student.getCpf()))) + 1)]).replace("\0", "0") + Long.toString(student.getCpf());
        String studentCPF =     studentCPFNoFormat.substring(0, 3)
                            +   "."
                            +   studentCPFNoFormat.substring(3, 6)
                            +   "."
                            +   studentCPFNoFormat.substring(6, 9)
                            +   "-"
                            +   studentCPFNoFormat.substring(9);
        
        //Gera uma string com a data de nascimento formatada
        String studentNascimento = student.getNascimento(true).substring(0, 2) 
                                   + "/" + student.getNascimento(true).substring(2, 4)
                                   + "/" + student.getNascimento(true).substring(4);
        
        //Separa as alergias/doenças/medicações por vírgulas
        String[] allergies = student.getAlergia(true).split("\\s*,\\s*");
        String[] diseases = student.getDoença(true).split("\\s*,\\s*");
        String[] medications = student.getMedicacao(true).split("\\s*,\\s*");
        //alergias, doenças e medicações em uma array de String
        String[] all_dis_med = concatAll(allergies, diseases, medications);
        
        //Correção no eixo Y para se adequar à altura da fonte
        int yCorrection = fontMetrics.getHeight() / 2;
        
        g2d.drawString(student.getNome(true), NAME_X, yCorrection + NAME_Y);
        g2d.drawString(studentNascimento, BDAY_X, yCorrection + BDAY_Y);
        g2d.drawString(studentSex, SEX_X, yCorrection + SEX_Y);
        g2d.drawString(studentCPF, CPF_X, yCorrection + CPF_Y);
        g2d.drawString(studentAssociation, ASSOC_X, yCorrection + ASSOC_Y);

        int temp = fontSize;
        
        /*
         *  Os campo de alergias/doenças/medicações e RH precisam aproveitar melhor o espaço
         *  então o tamanho da fonte é reduzido temporariamente para esses campos (~90% do tamanho original)
         */
        setFontSize(Math.round(fontSize * 90 / 100f));
        // A correção em Y precisa ser atualizada, já que a altura da fonte foi alterada
        yCorrection = fontMetrics.getHeight() / 2;
        
        g2d.drawString(student.getRh(true), RH_X, yCorrection + RH_Y);
        
        // Posição inicial para as strings de alergias/doenças/medicações
        int x = MED_X, y = MED_Y;
        
        /*
         *  Insere strings sobre alergias/doenças/medicações no campo da carteirinha
         *  de modo que as strings não extrapolem as laterais (pode extrapolar o fundo) do campo
         */
        for(String str : all_dis_med)
        {
            // Se não couber a próxima string + uma vírgula, então a posição x é resetada e y vai para  próxima linha
            if(x + fontMetrics.stringWidth(str + ",") > ALL_DIS_MED_X_MAX)
            {
                x = MED_X;
                y += fontMetrics.getHeight();
            }
            
            //Se não for a última string do array, então concatenar uma vírgula e um espaço
            if(!(str.equals(all_dis_med[all_dis_med.length - 1])))
            {
                g2d.drawString(str+ ", ", x, yCorrection + y);
            }
            else
            {
                g2d.drawString(str, x, yCorrection + y);
            }
            
            //Incremento da posição x pela largura da palavra
            x += fontMetrics.stringWidth(str + ", ");
        }
        
        //O tamanho da fonte é restaurado
        setFontSize(temp);
        //A carteirinha é salva como pdf com o nome do estudante + seu CPF para desambiguação
        saveAsPDF(student.getNome(true).replace(' ', '-') + '_' +studentCPFNoFormat + ".pdf");
    
        dispose();
    }
    
    /**
     * Salva a imagem da carteirinha (PNG) como PDF
     * @param fileName Nome do arquivo a ser criado para a carteirinha
     */
    private void saveAsPDF(String fileName)
    {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        
        try
        {
            PDImageXObject pdImageXObject = LosslessFactory.createFromImage(doc, image);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, false);
            contentStream.drawImage(pdImageXObject, 200, 300, image.getWidth() / 2, image.getHeight() / 2);
            contentStream.close();
            doc.save(fileName);
            doc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Concatena todas as strings de todos os arrays de entrada em um único array de strings
     * @param stringsArray Um varargs de array de strings (ou um array de array de string)
     * @return Um array de strings contendo todos os strings de todos os arrays-parâmetros
     */
    private String[] concatAll(String[]... stringsArray) {
        int len = 0;
        for (final String[] strings : stringsArray) {
            len += strings.length;
        }

        final String[] result = new String[len];

        int currentPos = 0;
        for (final String[] strings : stringsArray) {
            System.arraycopy(strings, 0, result, currentPos, strings.length);
            currentPos += strings.length;
        }

        return result;
    }

    /**
     * Construtor - carrega a imagem da carteirinha em branco e os valores padrões de fonte e cor da fonte
     */
    public AtleticaMembershipCard()
    {
        try
        {
            blankID = ImageIO.read(new File("img/Carteirinha.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        //Valores padrões para a fonte e a cor da fonte
        font = new Font("Serif", Font.PLAIN, fontSize);
        fontColor = Color.black;
    }
    
    /**
     * Inicializa a imagem como uma carteirinha em branco para o Graphics2D poder desenhar em cima
     */
    private void initialize()
    {
        image = copyBI(blankID);
        g2d = image.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setColor(fontColor);
        g2d.setFont(font);
        fontMetrics = g2d.getFontMetrics();
    }
    
    /**
     * Copia uma BufferedImage em outra
     * @param src Imagem de origem
     * @return Imagem copiada
     */
    private BufferedImage copyBI(BufferedImage src)
    {
        ColorModel cm = src.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = src.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    public void setFont(String fontName)
    {
    	this.fontName = fontName;
    	font = new Font(fontName, Font.PLAIN, fontSize);
    	g2d.setFont(font);
    	fontMetrics = g2d.getFontMetrics();
    }
    
    public void setFontSize(int fontSize)
    {
    	this.fontSize = fontSize;
    	font = new Font(fontName, Font.PLAIN, fontSize);
    	g2d.setFont(font);
    	fontMetrics = g2d.getFontMetrics();
    }
    
    public void setFontColor(Color color)
    {
    	this.fontColor = color;
    	g2d.setColor(color);
    }
        
    private void dispose()
    {
        g2d.dispose();
    }
}