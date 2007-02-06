/*
 * WeatherFrame.java
 *
 * Created on February 6, 2007, 4:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package simpleweather;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.net.*;
import javax.swing.*;
import simpleweather.ws.WeatherData;

/**
 * Members are package (default) protected to allow unit testing.
 * @author Sean Melody
 */
public class WeatherFrame extends JFrame {
    
    
    /**
     * The text field used to enter the zip code to
     * get the weather of.
     */
    JTextField m_txtZipCode = new JTextField();
    
    JPanel m_panelResults = new JPanel();
    
    /** Creates a new instance of WeatherFrame */
    public WeatherFrame() {
        super("A simple weather application");
        initializeListeners();
        initializeGUI();
        setVisible( true );
    }
    
    /**
     * Initializes the event and action listeners used in this frame.
     */
    private void initializeListeners() {
        m_txtZipCode.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent ev ) {
                txtZipCode_actionPerformed( ev);
            }
        } );
        
        // Only allow numerical input.
        m_txtZipCode.setInputVerifier(new IntegerInputVerifier());
    }
    
    /**
     * Initializes all of the GUI elements used by this frame.
     */
    private void initializeGUI() {
        
        
        JPanel panelTop = new JPanel( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        constraints.weightx = .1;
        constraints.fill = GridBagConstraints.BOTH;
        panelTop.add( new JLabel( "Zip Code:"), constraints );
        
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weighty = .9;
      
        m_txtZipCode.setColumns(10);
        panelTop.add( m_txtZipCode, constraints);
        
        JPanel panelMain = new JPanel( new BorderLayout() );
        panelMain.add( panelTop, BorderLayout.NORTH );
        m_panelResults.add( new JLabel("Results will appear here"));
        panelMain.add( m_panelResults, BorderLayout.CENTER );
        setContentPane( panelMain );
        pack();
    }
    /**
     * The action listener when enter is pressed in the zip code text field.
     * @param ev The action event.
     */
    void txtZipCode_actionPerformed( ActionEvent ev ) {
        try { // Call Web Service Operation
            simpleweather.ws.WeatherForecast service = new simpleweather.ws.WeatherForecast();
            simpleweather.ws.WeatherForecastSoap port = service.getWeatherForecastSoap();
            // TODO initialize WS operation arguments here
            java.lang.String zipCode = m_txtZipCode.getText();
            // TODO process result here
            simpleweather.ws.WeatherForecasts result = port.getWeatherByZipCode(zipCode);
            
            
            List<WeatherData> listData = result.getDetails().getWeatherData();
            int nMaxRows = listData.size();
            initializeResultsPanel( nMaxRows );
            
            
            for ( WeatherData data : listData ) {
                String sFullDay = data.getDay();
                int nCommaIndex = sFullDay.indexOf(',');
                String sDay = sFullDay.substring(0 , nCommaIndex );
                
                String sImg = data.getWeatherImage();
                URL urlImage = new URL( sImg );
             
                JLabel lblDay = new JLabel( sDay );
                Icon icon = new ImageIcon( urlImage );
                lblDay.setIcon( icon );
      
                m_panelResults.add( lblDay);
                String sMaxF = data.getMaxTemperatureF();
                m_panelResults.add( new JLabel( sMaxF));
                
                String sMinF = data.getMinTemperatureF();
                m_panelResults.add( new JLabel( sMinF ) );
                
                
            }
            
            pack();
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        
       
    }
    
      /**
   * Close when the close button (X) is pressed. Otherwise, call on the superclass
   * method.
   * @param ev The window event.
   */
  @Override
  protected void processWindowEvent( WindowEvent ev )
  {
    if ( ev.getID() == WindowEvent.WINDOW_CLOSING )
    {
      dispose();
    }
    else
    {
      super.processWindowEvent( ev );
    }

  }

  
  
  /**
   *Initializes the results panel for the maximum number of rows.
   *Adds a header row first.
   * @param nMaxRows The maximum number of rows.  Determined by the weather web
   *service.
   */
  private void initializeResultsPanel( int nMaxRows ) {
      
      m_panelResults.removeAll();
      GridLayout layout = new GridLayout( nMaxRows + 1, 3, 5, 5 );
      
      m_panelResults.setLayout( layout );
            
     JLabel lblDay = new JLabel( "Day");
     
     Font font = lblDay.getFont();
     Font fontBold = new Font(font.getName(), Font.BOLD, font.getSize() +2 );
     
     lblDay.setFont( fontBold );
     JLabel lblMax = new JLabel ( "Max temperature (F)");
     lblMax.setFont( fontBold );
     
     JLabel lblMin = new JLabel ( "Min temperature (F)");
     lblMin.setFont( fontBold );
             
    m_panelResults.add( lblDay);
     m_panelResults.add( lblMax);
     m_panelResults.add( lblMin );
     
  }
}
