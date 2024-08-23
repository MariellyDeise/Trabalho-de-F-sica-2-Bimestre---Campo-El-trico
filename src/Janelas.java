import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Janelas{
    static int raio_int;
    static int numeroContas_int;
    static int[] angulo;
    static int[] q;
    static double EFinal = 0;
    static double Ex;
    static double Ey;
    static double vetUnitario = 0;
    static JFrame janela;
    static JFrame frame;
    static JTextField[] cargaField;
    static JTextField[] anguloField;

    public Janelas(){
        criando();
    }

    public void criando(){

        janela = new JFrame("Trabalho Física 2 bimestre - Marielly Deise");
        janela.setVisible(true);
        janela.setSize(515, 380);// regula o tamanho da caixa.
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// quando fecha a caixa o programa finaliza.
        janela.setLocationRelativeTo(null);
        janela.setLayout(null);

        JLabel informacoes = new JLabel("<html>Um anel de plástico de raio R [20; 200] contem distribuidas sobre ele N [1; 10] contas, com localizaçao (θ) e carga (μC) a definir. As contas juntas produzem um campo elétrico de módulo E no centro do anel. Qual a carga (Q) do campo elétrico?<html>");
        informacoes.setBounds(30, 20, 450, 80);
        janela.add(informacoes);

        JLabel raio = new JLabel("Raio do anel (cm): ");
        raio.setBounds(30, 100, 150, 20);
        janela.add(raio);

        JTextField raio_text = new JTextField();
        raio_text.setBounds(140, 100, 80, 20);
        janela.add(raio_text);

        JLabel numeroConta = new JLabel("Numero de contas: ");
        numeroConta.setBounds(250, 100, 150, 20);
        janela.add(numeroConta);

        JTextField numeroConta_text = new JTextField();
        numeroConta_text.setBounds(370, 100, 80, 20);
        janela.add(numeroConta_text);
    
   
        janela.revalidate();
        janela.repaint();


        JButton gerarCamposButton = new JButton("Gerar Contas");
        gerarCamposButton.setBounds(350, 130, 115, 20);
        janela.add(gerarCamposButton);
        janela.revalidate(); 
        janela.repaint();

        JButton ok = new JButton("Ok");
        ok.setBounds(340, 300, 50, 20);
        janela.add(ok);
        janela.revalidate(); 
        janela.repaint();

        JButton sair = new JButton("Sair");
        sair.setBounds(400, 300, 80, 20);
        janela.add(sair);
        janela.revalidate(); 
        janela.repaint();


        // Painel onde os campos serão adicionados
        JPanel camposPanel = new JPanel();
        camposPanel.setBounds(80, 150, 350, 200);
        camposPanel.setOpaque(false);// panel invisivel.
        
        janela.add(camposPanel);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                raio_int = Integer.parseInt(raio_text.getText());
                    numeroContas_int = Integer.parseInt(numeroConta_text.getText());
                    angulo = new int[numeroContas_int];
                    q = new int[numeroContas_int];

                    for (int i = 0; i < numeroContas_int; i++) {
                        if(cargaField[i].getText().isEmpty() || anguloField[i].getText().isEmpty()){
                            JOptionPane.showMessageDialog(null, "Preencha todos os espaços.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        q[i] = Integer.parseInt(cargaField[i].getText());
                        angulo[i] = Integer.parseInt(anguloField[i].getText());
                    }
                    
                    // Criação e configuração do novo JFrame para o gráfico
                    JFrame graficoFrame = new JFrame("Gráfico");
                    graficoFrame.setSize(800, 600);
                    graficoFrame.setResizable(false);
                    graficoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    graficoFrame.setLocationRelativeTo(null);
                    

                    double[] E = new double[numeroContas_int];
                    double[] vetE = new double[numeroContas_int];

                    String strK = "9e9"; // 9 × 10^9
                    double num = Double.parseDouble(strK);
                    double raio_double = (double) raio_int/100;

                    Ex = 0;
                    Ey = 0;

                    for(int i = 0; i < numeroContas_int; i++){
                        E[i] = (num * (q[i] * (Math.pow(10, -6)))) / Math.pow(raio_double, 2.0);
                        double ang = angulo[i]*Math.PI/180;
                        Ex += -(E[i] * Math.cos(ang));//não sei se é negativo ou positivo.
                        Ey += -(E[i] * Math.sin(ang));
                        //vetE[i] = -(E[i] * Math.cos(ang)) - (E[i] * Math.sin(ang));
                        //EFinal += vetE[i];
                        System.out.println(Ex);
                    }

                    vetUnitario = Ex * Ex + Ey * Ey;
                    vetUnitario = Math.sqrt(vetUnitario);//mostrar no panel.
                    Ex /= vetUnitario;
                    Ey /= vetUnitario;
                    

                     // Criação do painel gráfico e adição ao JFrame
                     Grafico2 circleGraph = new Grafico2(raio_int, numeroContas_int, angulo, Ex, Ey);
                     circleGraph.setOpaque(false);// panel invisivel.
                     graficoFrame.add(circleGraph);
                     graficoFrame.setVisible(true);

                    String notacaoCientifica = String.format("%.2e", vetUnitario);
                    notacaoCientifica = notacaoCientifica.replace("e", " * 10^");

                    JLabel ETotal = new JLabel("Q (μC): " + notacaoCientifica);
                    circleGraph.setLayout(null);
                    ETotal.setFont(new Font("Arial", Font.BOLD, 15));
                    ETotal.setBounds(30, 10, 250, 20);
                    circleGraph.add(ETotal);

                    graficoFrame.setVisible(true);
                    janela.dispose(); // Fecha a janela principal
            }
        });

        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(janela, "Tem certeza que deseja sair?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    janela.dispose(); // Fecha a janela
                }
            }
        });

        gerarCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int raio_int = Integer.parseInt(raio_text.getText());
                    if (raio_int < 20 || raio_int > 200) {
                        JOptionPane.showMessageDialog(janela, "O raio do anel dever estar entre 20 e 200.", "Erro!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Obtemos o número de contas inserido
                    numeroContas_int = Integer.parseInt(numeroConta_text.getText());
                    if (numeroContas_int < 1 || numeroContas_int > 10) {
                        JOptionPane.showMessageDialog(janela, "O número de contas deve ser entre 1 e 10.", "Erro!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Limpa o painel de campos antigos
                    camposPanel.removeAll();
                    cargaField = new JTextField[numeroContas_int];
                    anguloField = new JTextField[numeroContas_int];

                    // Adiciona novos campos
                    for (int i = 0; i < numeroContas_int; i++) {
                        JLabel cargaLabel = new JLabel("q" + i + " (μC):");
                        cargaLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Alinha a label à direita
                        cargaField[i] = new JTextField(3);
                        cargaField[i].setPreferredSize(new Dimension(60, 20)); // Define o tamanho preferido da caixa de texto

                        JLabel anguloLabel = new JLabel("θ" + i  + " (°):");
                        anguloLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                        anguloField[i] = new JTextField(3);
                        anguloField[i].setPreferredSize(new Dimension(60, 20));


                        camposPanel.add(cargaLabel);
                        camposPanel.add(cargaField[i]);
                        camposPanel.add(anguloLabel);
                        camposPanel.add(anguloField[i]);
                    }

                    // Atualiza o layout do painel
                    camposPanel.revalidate();
                    camposPanel.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(janela, "Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        janela.setVisible(true);
    }

    public static void main(String[] args) {
        new Janelas();
    }
}

class Grafico2 extends JPanel {
    private int raio;
    private int conta;
    private int[] angulos;
    private double Ex;
    private double Ey;

    public Grafico2(int raio, int contas, int[] angulos, double Ex, double Ey) {
        this.raio = raio;
        this.conta = contas;
        this.angulos = angulos;
        this.Ex = Ex;
        this.Ey = Ey;
        setPreferredSize(new Dimension(400, 400)); // Ajuste o tamanho do painel
        
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Desenha os eixos X e Y
        int width = getWidth();
        int height = getHeight();
        int centroX = width / 2;
        int centroY = height / 2;

        g2d.drawLine(0, centroY, width, centroY); // Eixo X
        g2d.drawLine(centroX, 0, centroX, height); // Eixo Y

        // Desenha o círculo centrado no gráfico
        int x = centroX - raio;
        int y = centroY - raio;
        g2d.drawOval(x, y, 2 * raio, 2 * raio);

        // Desenhar pontos nos locais calculados
        for (int i = 0; i < conta; i++) {
            double anguloRad = Math.toRadians(angulos[i]);
            double x1 = raio * Math.cos(anguloRad);
            int pontox = (int) Math.round(x1 + centroX);
            double y1 = raio * Math.sin(anguloRad);
            int pontoy = (int) Math.round(centroY - y1);
            desenharPonto(g2d, pontox, pontoy);
        }

         // Desenha um vetor no plano cartesiano
         g2d.setColor(Color.BLUE); // Define a cor do vetor para azul// Ponto final do vetor no eixo Y
         System.out.println(Ex + "   " + Ey);
         double finalX = centroX + Ex * raio;  
         double finalY = centroY - Ey * raio; 

        System.out.println(finalX + "   " + finalY);

         g2d.draw(new Line2D.Double(centroX, centroY, finalX, finalY)); // Desenha a linha do vetor
         drawArrowHead(g2d, finalX, finalY, centroX, centroY);
    }

    private void desenharPonto(Graphics2D g2d, int pontox, int pontoy) {
        g2d.setColor(Color.RED);
        int tamanhoPonto = 6; // Tamanho do ponto
        g2d.fillOval((int) Math.round(pontox) - tamanhoPonto / 2, 
                     (int) Math.round(pontoy) - tamanhoPonto / 2, 
                     tamanhoPonto, tamanhoPonto);
    }

    private void drawArrowHead(Graphics2D g2d, double tipX, double tipY, double tailX, double tailY) {
        double dx = tipX - tailX;
        double dy = tipY - tailY;
        double angle = Math.atan2(dy, dx);
        int arrowLength = 10;
        int arrowWidth = 7;

        double x1 = tipX - arrowLength * Math.cos(angle + Math.PI / 6);
        double y1 = tipY - arrowLength * Math.sin(angle + Math.PI / 6);
        double x2 = tipX - arrowLength * Math.cos(angle - Math.PI / 6);
        double y2 = tipY - arrowLength * Math.sin(angle - Math.PI / 6);

        // Desenha as linhas da cabeça da flecha
        g2d.drawLine((int) Math.round(tipX), (int) Math.round(tipY), (int) Math.round(x1), (int) Math.round(y1));
        g2d.drawLine((int) Math.round(tipX), (int) Math.round(tipY), (int) Math.round(x2), (int) Math.round(y2));
    }
}


