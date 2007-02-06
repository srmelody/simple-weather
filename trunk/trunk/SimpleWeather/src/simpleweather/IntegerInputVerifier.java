
package simpleweather;

import javax.swing.*;

/**
 * An input verifier for the innings text fields. This will ensure the values
 * entered are integer.
 *
 */
public class IntegerInputVerifier extends InputVerifier
{

  /**
   * Restricts the input of the text field to integers and backspace + delete.
   * @param comp The component to verify
   * @return True if the given text field is valid.
   * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
   */
  @Override
  public boolean verify( JComponent comp )
  {
    boolean bValid = false;

    if ( comp instanceof JTextField )
    {
      JTextField textField = ( JTextField ) comp;
      String sText = textField.getText();

      if ( sText.length() != 0 )
      {
        try
        {
          Integer.parseInt( sText );
          bValid = true;
        }
        catch ( NumberFormatException e )
        {
          // Do nothing, bValid is already false.
        }
      }
    }
    return bValid;

  }
}
