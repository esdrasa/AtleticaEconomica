package modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
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
    private Font assocFont;
    private Color fontColor;
    private String fontName;
    private FontMetrics fontMetrics;
    private int fontSize = 14;
    
    String path;

    private static final int NAME_X1 = 164;
    private static final int NAME_X2 = 546;
    private static final int NAME_Y1 = 515;
    private static final int NAME_Y2 = 535;
    private static final int BDAY_X1 = 164;
    private static final int BDAY_X2 = 546;
    private static final int BDAY_Y1 = 636;
    private static final int BDAY_Y2 = 656;
    private static final int SEX_X1 = 164;
    private static final int SEX_X2 = 546;
    private static final int SEX_Y1 = 636;
    private static final int SEX_Y2 = 656;
    private static final int CPF_X1 = 164;
    private static final int CPF_X2 = 546;
    private static final int CPF_Y1 = 574;
    private static final int CPF_Y2 = 594;
    private static final int MATR_X1 = 164;
    private static final int MATR_X2 = 546;
    private static final int MATR_Y1 = 574;
    private static final int MATR_Y2 = 594;
    private static final int RH_X1 = 164;
    private static final int RH_X2 = 546;
    private static final int RH_Y1 = 515;
    private static final int RH_Y2 = 535;
    private static final int MED_X1 = 26;
    private static final int MED_X2 = 546;
    private static final int MED_Y1 = 691;
    private static final int MED_Y2 = 711;
    private static final int ASSOC_X1 = 142;
    private static final int ASSOC_X2 = 429;
    private static final int ASSOC_Y1 = 445;
    private static final int ASSOC_Y2 = 470;
    private static final int ALL_DIS_MED_X1 = 275;
    private static final int ALL_DIS_MED_X2 = 275;
    private static final int ALL_DIS_MED_Y1 = 275;
    private static final int ALL_DIS_MED_Y2 = 275;
    
    private static final int PADDING_X = 5;
    private static final int PADDING_Y = 3;

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
            studentSex = "Feminino";
        }
        else
        {
            studentSex = "Masculino";
        }
        
        //Definindo a string que representa o Vínculo do aluno
        String studentAssociation;
        
        switch(student.getVinculo())
        {
            case 0:
                studentAssociation = "- A s s o c i a d o -";
                break;
            case 1:
                studentAssociation = "- A t l e t a -";
                break;
            default:
                studentAssociation = "- A t l e t a   A s s o c i a d o -";
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
        String studentBDay = student.getNascimento(true).substring(0, 2) 
                                   + "/" + student.getNascimento(true).substring(2, 4)
                                   + "/" + student.getNascimento(true).substring(4);
        
        //Separa as alergias/doenças/medicações por vírgulas
        String[] allergies = student.getAlergia(true).split("\\s*,\\s*");
        String[] diseases = student.getDoença(true).split("\\s*,\\s*");
        String[] medications = student.getMedicacao(true).split("\\s*,\\s*");
        //alergias, doenças e medicações em uma array de String
        String[] _all_dis_med = concatAll(allergies, diseases, medications);
        
        String all_dis_med = "";
        
        for(String s : _all_dis_med)
        {
            all_dis_med += s + ", ";
            
        }
        
        all_dis_med = all_dis_med.substring(0, all_dis_med.length() - 2);
        
        drawFieldText(student.getNome(true), 'l', NAME_X1, NAME_X2, NAME_Y1, NAME_Y2);
        drawFieldText(student.getRh(true), 'r', RH_X1, RH_X2, RH_Y1, RH_Y2);
        drawFieldText(studentCPF, 'l', CPF_X1, CPF_X2, CPF_Y1, CPF_Y2);
        drawFieldText(Long.toString(student.getMatricula()), 'r', MATR_X1, MATR_X2, MATR_Y1, MATR_Y2);
        drawFieldText(studentSex, 'l', SEX_X1, SEX_X2, SEX_Y1, SEX_Y2);
        drawFieldText(studentBDay, 'r', BDAY_X1, BDAY_X2, BDAY_Y1, BDAY_Y2);
        drawFieldText(all_dis_med, 'c', MED_X1, MED_X2, MED_Y1, MED_Y2);
        
        g2d.setFont(assocFont);
    	fontMetrics = g2d.getFontMetrics();
        
        drawFieldText(studentAssociation, 'c', ASSOC_X1, ASSOC_X2, ASSOC_Y1, ASSOC_Y2);
        
        g2d.setFont(font);
        fontMetrics = g2d.getFontMetrics();
        
        //A carteirinha é salva como pdf com o nome do estudante + seu CPF para desambiguação
        saveAsPDF(path + "PDFs Gerados" + File.separator + student.getNome(true).replace(' ', '-') + '_' +studentCPFNoFormat + ".pdf");
        dispose();
    }
    
    private void drawFieldText(String text, char alignment, int x1, int x2, int y1, int y2)
    {
        int x, y = y1 + fontSize;
        y += (y2 - y) / 2 - PADDING_Y;
        
        switch (alignment)
        {
            case 'l':
                x = x1 + PADDING_X;
                break;
            case 'r':
                x = x2 - fontMetrics.stringWidth(text) - PADDING_X;
                break;
            case 'c':
                x = ((x1 + x2) / 2) - fontMetrics.stringWidth(text) / 2;
                break;
            default:
                return;
        }
        
        g2d.drawString(text, x, y);
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
    public AtleticaMembershipCard() throws FontFormatException, IOException
    {
        try {
            this.path = new File(AtleticaMembershipCard.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent()+File.separator;
        } catch (URISyntaxException ex) {
            Logger.getLogger(AtleticaMembershipCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            blankID = ImageIO.read(new File(path+"res"+File.separator+"Carteirinha.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            assocFont = Font.createFont(Font.TRUETYPE_FONT, new File(path+"res"+File.separator+"CaviarDreams.ttf")).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(path+"res"+File.separator+"CaviarDreams.ttf")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
        //Valores padrões para a fonte e a cor da fonte
        font = new Font("Arial", Font.BOLD, fontSize);
        fontColor = Color.white;
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
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
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