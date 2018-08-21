import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.awt.GridLayout;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
/**
 * This is a GUI program for easily making folders using specific options for 
 * making folders for music albums
 * 
 * @author Jonathan
 * @version 1.0
 */
public class make_folder extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5158326257568382410L;
	private JTextField textArtist;
	private JTextField textReleaseName;
	private JTextField textYear;
	private JTextField textEdition;
	private JComboBox<Object> comboCodec;
	private JComboBox<Object> comboSource;
	private JButton btnClearAll;
	private JTextArea FolderName;
	private JButton btnApply;
	
	private AudioFile audioFile;
	private Tag tag;
	private AudioHeader AH;
	private JTextField txtrDragFileHere;
	private JButton btnCopyToClipboard;
	public make_folder() {
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(SystemColor.menu);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{160, 106, 106, 187, 0};
		gridBagLayout.rowHeights = new int[]{29, 29, 29, 29, 29, 34, 25, 46, 25, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		
		// CLEAR ALL Button clears all boxs
		btnClearAll = new JButton("CLEAR ALL");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArtist.setText("");
				textYear.setText("");
				textEdition.setText("");
				textReleaseName.setText("");
				comboSource.setSelectedIndex(0);
				comboCodec.setSelectedIndex(0);
				FolderName.setText("");
			}
		});
		
		JLabel releaseName = new JLabel("Release Name");
		releaseName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		releaseName.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_releaseName = new GridBagConstraints();
		gbc_releaseName.anchor = GridBagConstraints.NORTH;
		gbc_releaseName.fill = GridBagConstraints.HORIZONTAL;
		gbc_releaseName.insets = new Insets(0, 0, 5, 5);
		gbc_releaseName.gridx = 0;
		gbc_releaseName.gridy = 0;
		getContentPane().add(releaseName, gbc_releaseName);
		
		textReleaseName = new JTextField();
		textReleaseName.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textReleaseName = new GridBagConstraints();
		gbc_textReleaseName.fill = GridBagConstraints.BOTH;
		gbc_textReleaseName.insets = new Insets(0, 0, 5, 5);
		gbc_textReleaseName.gridx = 1;
		gbc_textReleaseName.gridy = 0;
		getContentPane().add(textReleaseName, gbc_textReleaseName);
		textReleaseName.setColumns(10);
		GridBagConstraints gbc_btnClearAll = new GridBagConstraints();
		gbc_btnClearAll.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClearAll.insets = new Insets(0, 0, 5, 0);
		gbc_btnClearAll.gridx = 3;
		gbc_btnClearAll.gridy = 0;
		getContentPane().add(btnClearAll, gbc_btnClearAll);
		
		JLabel date = new JLabel("Release Date");
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_date = new GridBagConstraints();
		gbc_date.anchor = GridBagConstraints.NORTH;
		gbc_date.insets = new Insets(0, 0, 5, 5);
		gbc_date.gridx = 0;
		gbc_date.gridy = 1;
		getContentPane().add(date, gbc_date);
		
		textYear = new JTextField();
		textYear.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textYear = new GridBagConstraints();
		gbc_textYear.fill = GridBagConstraints.BOTH;
		gbc_textYear.insets = new Insets(0, 0, 5, 5);
		gbc_textYear.gridx = 1;
		gbc_textYear.gridy = 1;
		getContentPane().add(textYear, gbc_textYear);
		textYear.setColumns(10);
		
		btnCopyToClipboard = new JButton("COPY TO CLIPBOARD");
		btnCopyToClipboard.setToolTipText("Copy Folder Name to the Clipboard");
		btnCopyToClipboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection stringSelection = new StringSelection(FolderName.getText());
				Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				clpbrd.setContents(stringSelection, null);
			}
		});
		GridBagConstraints gbc_btnCopyToClipboard = new GridBagConstraints();
		gbc_btnCopyToClipboard.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCopyToClipboard.insets = new Insets(0, 0, 5, 0);
		gbc_btnCopyToClipboard.gridx = 3;
		gbc_btnCopyToClipboard.gridy = 1;
		getContentPane().add(btnCopyToClipboard, gbc_btnCopyToClipboard);
		
		JLabel source = new JLabel("Source");
		source.setFont(new Font("Tahoma", Font.PLAIN, 20));
		source.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_source = new GridBagConstraints();
		gbc_source.anchor = GridBagConstraints.NORTH;
		gbc_source.insets = new Insets(0, 0, 5, 5);
		gbc_source.gridx = 0;
		gbc_source.gridy = 2;
		getContentPane().add(source, gbc_source);
		
		comboSource = new JComboBox<Object>();
		comboSource.setModel(new DefaultComboBoxModel<Object>(new String[] {"CD", "WEB", "VINYL"}));
		GridBagConstraints gbc_comboSource = new GridBagConstraints();
		gbc_comboSource.fill = GridBagConstraints.BOTH;
		gbc_comboSource.insets = new Insets(0, 0, 5, 5);
		gbc_comboSource.gridx = 1;
		gbc_comboSource.gridy = 2;
		getContentPane().add(comboSource, gbc_comboSource);
		//Sets text box to new values
		btnApply = new JButton("APPLY");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnApply = new GridBagConstraints();
		gbc_btnApply.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApply.insets = new Insets(0, 0, 5, 0);
		gbc_btnApply.gridx = 3;
		gbc_btnApply.gridy = 2;
		getContentPane().add(btnApply, gbc_btnApply);
		
		JLabel codec = new JLabel("Codec");
		codec.setHorizontalAlignment(SwingConstants.CENTER);
		codec.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_codec = new GridBagConstraints();
		gbc_codec.anchor = GridBagConstraints.NORTH;
		gbc_codec.insets = new Insets(0, 0, 5, 5);
		gbc_codec.gridx = 0;
		gbc_codec.gridy = 3;
		getContentPane().add(codec, gbc_codec);
		
		comboCodec = new JComboBox<Object>();
		comboCodec.setModel(new DefaultComboBoxModel<Object>(new String[] {"FLAC","24bit FLAC" ,"320" , "V0", "V2","AAC" }));
		GridBagConstraints gbc_comboCodec = new GridBagConstraints();
		gbc_comboCodec.fill = GridBagConstraints.BOTH;
		gbc_comboCodec.insets = new Insets(0, 0, 5, 5);
		gbc_comboCodec.gridx = 1;
		gbc_comboCodec.gridy = 3;
		getContentPane().add(comboCodec, gbc_comboCodec);
		
		JLabel edition = new JLabel("Edition");
		edition.setHorizontalAlignment(SwingConstants.CENTER);
		edition.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_edition = new GridBagConstraints();
		gbc_edition.anchor = GridBagConstraints.NORTH;
		gbc_edition.insets = new Insets(0, 0, 5, 5);
		gbc_edition.gridx = 0;
		gbc_edition.gridy = 4;
		getContentPane().add(edition, gbc_edition);
		
		textEdition = new JTextField();
		textEdition.setText("");
		GridBagConstraints gbc_textEdition = new GridBagConstraints();
		gbc_textEdition.fill = GridBagConstraints.BOTH;
		gbc_textEdition.insets = new Insets(0, 0, 5, 5);
		gbc_textEdition.gridx = 1;
		gbc_textEdition.gridy = 4;
		getContentPane().add(textEdition, gbc_textEdition);
		textEdition.setColumns(10);
		
		JLabel artist = new JLabel("Artist");
		artist.setFont(new Font("Tahoma", Font.PLAIN, 20));
		artist.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_artist = new GridBagConstraints();
		gbc_artist.fill = GridBagConstraints.BOTH;
		gbc_artist.insets = new Insets(0, 0, 5, 5);
		gbc_artist.gridx = 0;
		gbc_artist.gridy = 5;
		getContentPane().add(artist, gbc_artist);
		
		textArtist = new JTextField();
		textArtist.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textArtist = new GridBagConstraints();
		gbc_textArtist.fill = GridBagConstraints.BOTH;
		gbc_textArtist.insets = new Insets(0, 0, 5, 5);
		gbc_textArtist.gridx = 1;
		gbc_textArtist.gridy = 5;
		getContentPane().add(textArtist, gbc_textArtist);
		textArtist.setColumns(10);
		
		FolderName = new JTextArea();
		FolderName.setEditable(false);
		GridBagConstraints gbc_FolderName = new GridBagConstraints();
		gbc_FolderName.fill = GridBagConstraints.BOTH;
		gbc_FolderName.insets = new Insets(0, 0, 5, 0);
		gbc_FolderName.gridwidth = 3;
		gbc_FolderName.gridx = 1;
		gbc_FolderName.gridy = 6;
		getContentPane().add(FolderName, gbc_FolderName);
		       //MOST IMPORTANT PART!!!!!!!!!!!
		       txtrDragFileHere = new JTextField("\r\nDRAG FILE HERE\r\n");
		       txtrDragFileHere.setToolTipText("Drag a music file here.");
		       txtrDragFileHere.setEditable(false);
		       txtrDragFileHere.setBackground(Color.RED);
		       txtrDragFileHere.setForeground(Color.BLACK);
		       txtrDragFileHere.setHorizontalAlignment(SwingConstants.CENTER);
		       GridBagConstraints gbc_txtrDragFileHere = new GridBagConstraints();
		       gbc_txtrDragFileHere.fill = GridBagConstraints.BOTH;
		       gbc_txtrDragFileHere.insets = new Insets(0, 0, 5, 5);
		       gbc_txtrDragFileHere.gridx = 0;
		       gbc_txtrDragFileHere.gridy = 7;
		       getContentPane().add(txtrDragFileHere, gbc_txtrDragFileHere);
		       
		       //Get audio tags from dropped file. Fill in boxes.
		              new FileDrop( txtrDragFileHere, new FileDrop.Listener()
		              {   public void filesDropped( java.io.File[] files )
		                  {   
		                     try {
		       					audioFile = AudioFileIO.read(new File(files[0].getCanonicalPath()));
		       					
		       				} catch (CannotReadException e) {
		       					System.out.println("CannotReadException");
		       				} catch (IOException e) {
		       					System.out.println("IOException");							
		       				} catch (TagException e) {
		       					System.out.println("TagException");	
		       				} catch (ReadOnlyFileException e) {
		       					System.out.println("ReadOnlyFileException");	
		       				} catch (InvalidAudioFrameException e) {
		       					System.out.println("InvalidAudioFrameException");	
		       				} 
		       				
		       				tag = audioFile.getTag();
		       				AH = audioFile.getAudioHeader();
		       				textArtist.setText(tag.getFirst(FieldKey.ARTIST));
		       				textReleaseName.setText(tag.getFirst(FieldKey.ALBUM));
		       				textYear.setText(tag.getFirst(FieldKey.YEAR));
		       				System.out.println(AH.getEncodingType());
		       				if(AH.getEncodingType().equals("FLAC 16 bits")){
		       					comboSource.setSelectedIndex(0);
		       					comboCodec.setSelectedIndex(0);
		       									}
		       				else if(AH.getEncodingType().equals("FLAC 24 bits")){
		       					comboSource.setSelectedIndex(1);
		       					comboCodec.setSelectedIndex(1);
		       				}
		       				else if(AH.getEncodingType().equals("mp3")){
		       					comboSource.setSelectedIndex(0);
		       					comboCodec.setSelectedIndex(2);
		       			
		       				}
		                  }   // end filesDropped
		              });
		    		
		    		
	}

	
	public static void main(String[] args) {
		make_folder window = new make_folder();
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.pack();
		window.setTitle("Easy Folder Name'er");
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
