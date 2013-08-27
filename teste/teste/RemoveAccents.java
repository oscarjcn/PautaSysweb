package teste;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.mpeg.drosofila.util.String.StringUtil;

public class RemoveAccents extends JFrame {
  public RemoveAccents() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    JPanel pnl = new JPanel();
    pnl.add(new JLabel("Enter text"));
    final JTextField txtText;
    txtText = new JTextField("to be removed");
    pnl.add(txtText);

    JButton btnRemove = new JButton("Remove");
    ActionListener al;
    al = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String text = txtText.getText();
        /*text = Normalizer.normalize(text, Normalizer.Form.NFD);        
        txtText.setText(text.replaceAll("[^\\p{ASCII}]", ""));*/
        String texto = TesteLimpaString.normalize(text);
        
        
        txtText.setText(StringUtil.apenasAlphaNumerico(texto));
        //txtText.setText(texto.replaceAll("[^\\p{ASCII}]", "").replaceAll(" ", "_").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("[\\\\]", "").replaceAll("[^A-z]", ""));
        //txtText.setText(TesteLimpaString.remover(texto, Arrays.asList(",","(",")", "&", "'", "*", "%", "$", "@", "!", "?", "{", "}", "[", "]","¨","º","°","#", "$","~","")));
        
        //txtText.setText(texto);
      }
    };
    btnRemove.addActionListener(al);
    pnl.add(btnRemove);

    getContentPane().add(pnl);

    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
    new RemoveAccents();
  }
}