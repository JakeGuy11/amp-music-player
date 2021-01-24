//Imports
import java.util.List;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFormat;

/**
 * @author Jake_Guy_11
 */
public class ampGUI extends javax.swing.JFrame {
    
    //Global Paths
    private Path musicPath = Paths.get(System.getProperty("user.home") + "/Music");
    private Path homePath = Paths.get(System.getProperty("user.home"));
    //The process builder for the video generating script
    ProcessBuilder videoBuilder;
    //The process builder for the audio generating script
    ProcessBuilder audioBuilder;
    //The process builder for the ffplay video
    ProcessBuilder ffVideoBuilder;
    //The process builder for the ffplay audio
    ProcessBuilder ffAudioBuilder;
    //The actual process for the video generating script
    Process videoProc;
    //The actual process for the audio generating script
    Process audioProc;
    //The actual process for the ffplay video
    Process ffVideoProc;
    //The actual process for the ffplay audio
    Process ffAudioProc;
    
    //Create JForm GUI
    public ampGUI() {
        //Do the built-in init
        initComponents();
        //Override the default close, to make sure all processes are closed
        
        //Generate Automatic Paths
        //Print the home directory
        System.out.println("User's home dir: " + homePath.toString());
        //Start searching for the music directory
        System.out.println("Checking searching for music directory...");
        //Check if the default music path exists (~/Music/)
        if (Files.notExists(musicPath)){
            //~/Music/ does not exist
            //Try ~/music with a lowercase 'm'
            musicPath = Paths.get(homePath.toString() + "/music");
            if (Files.notExists(musicPath)){
                //Both ~/Music and ~/music do not exist
                System.out.println("Music path not found. Using home directory");
                //Use the home directory as the music directory
                musicPath = homePath;
            } else {
                //~/music does exist
                System.out.println("User's music dir: " + musicPath.toString());
            }
        }//If the default music path does exist, leave it like that
        //Print the music path
        System.out.println("User's music dir: " + musicPath.toString());
        
        //Change the text in the path textBox to the actual music path
        this.dBoxMusicDir.setText(musicPath.toString());
        
        //Set the stopPlayback button to invisible so you can only click it once it's started
        this.dStopButton.setVisible(false);
    }

    //Auto-generated code. DO NOT MODIFY.
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dPanelMain = new javax.swing.JPanel();
        dTitlePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dLabelMusicDir = new javax.swing.JLabel();
        dBoxMusicDir = new javax.swing.JTextField();
        dButtonSelectDir = new javax.swing.JButton();
        dStartButton = new javax.swing.JButton();
        dStopButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        dTitlePanel.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html><h2>AMP Music Player</h1></html>");
        dTitlePanel.add(jLabel1, java.awt.BorderLayout.CENTER);

        dLabelMusicDir.setText("Select your music directory (non-recursive):");

        dBoxMusicDir.setText("/path/to/music");

        dButtonSelectDir.setText("...");
        dButtonSelectDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectDirSelected(evt);
            }
        });

        dStartButton.setText("Play");
        dStartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayMedia(evt);
            }
        });

        dStopButton.setText("Stop Playback");
        dStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopPlayback(evt);
            }
        });

        javax.swing.GroupLayout dPanelMainLayout = new javax.swing.GroupLayout(dPanelMain);
        dPanelMain.setLayout(dPanelMainLayout);
        dPanelMainLayout.setHorizontalGroup(
            dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dPanelMainLayout.createSequentialGroup()
                        .addComponent(dBoxMusicDir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dButtonSelectDir))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dPanelMainLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(dStopButton)
                        .addGap(18, 18, 18)
                        .addComponent(dStartButton))
                    .addGroup(dPanelMainLayout.createSequentialGroup()
                        .addComponent(dLabelMusicDir)
                        .addGap(0, 226, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(dTitlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dPanelMainLayout.setVerticalGroup(
            dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dPanelMainLayout.createSequentialGroup()
                .addComponent(dTitlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dLabelMusicDir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dBoxMusicDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dButtonSelectDir))
                .addGap(102, 102, 102)
                .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dStartButton)
                    .addComponent(dStopButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //The ... button was selected, let them choose a directory
    private void SelectDirSelected(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectDirSelected
        //Create a new instance of a file choose
        JFileChooser fileChooser = new JFileChooser();
        //Set the starting directory to the home directory
        fileChooser.setCurrentDirectory(new File(homePath.toUri()));
        //Set the window title to tell them to choose their music directory
        fileChooser.setDialogTitle("Select your desired music directory (recursive)");
        //Allow the dialogue to only choose directories
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //Show the file choose
        fileChooser.showOpenDialog(null);
        //Create a file with the selected directory
        File myDir = fileChooser.getSelectedFile();
        //Set a string to the absolute path of the selected directory
        String absoluteDir = myDir.getAbsolutePath();
        //Set the global music path to the selected music path
        musicPath = Paths.get(absoluteDir);
        //Change the text of the path box to the selected directory
        this.dBoxMusicDir.setText(musicPath.toString());
        //Print the selected directory
        System.out.println("Music dir selected: " + absoluteDir);
    }//GEN-LAST:event_SelectDirSelected
    
    //The play button was selected
    private void PlayMedia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayMedia
        //Just call the GenerateList method for testing
        generatePlayList(true, musicPath.toFile());
        //Create a string array with all the parts of the ffplay video command
        //This will be replaced with the generatePlayList method eventually, at least for the arguments
        //For now, vid1.ts and vid2.ts are just funny clips, but will be replaced in the future
        String[] videoArgs = new String[] {"/bin/bash","-c","./scripts/runVideo.sh vid1.ts vid2.ts"};
        //Create a string array with the names of the audio we want to play
        //For now, audio1.mp3 and audio2.mp3 are both test songs
        String[] audioArgs = new String[] {"/bin/bash","-c","./scripts/runAudio.sh audio1.mp3 audio2.mp3"};
        //Create a string array with all the commands for the ffplay command. This will not be changed dynamically
        String[] ffVideoArgs = new String[] {"/bin/bash","-c","ffplay -fs -loop -1 ./gen/videoOut.mp4"};
        String[] ffAudioArgs = new String[] {"/bin/bash","-c","ffplay -nodisp -loop -1 ./gen/audioOut.mp3"};
        try {
            //Create a new processbuilder made from the parts of the command we want to run for all 4 processes
            videoBuilder = new ProcessBuilder(videoArgs);
            audioBuilder = new ProcessBuilder(audioArgs);
            ffVideoBuilder = new ProcessBuilder(ffVideoArgs);
            ffAudioBuilder = new ProcessBuilder(ffAudioArgs);
            //Redirect any terminal output to the default location, in this case that's the console for all 4 processes
            videoBuilder.redirectOutput(Redirect.INHERIT);
            audioBuilder.redirectOutput(Redirect.INHERIT);
            ffVideoBuilder.redirectOutput(Redirect.INHERIT);
            ffAudioBuilder.redirectOutput(Redirect.INHERIT);
            //Redirect any terminal errors to the default location, in this case that's the console for all 4 processes
            videoBuilder.redirectError(Redirect.INHERIT);
            audioBuilder.redirectError(Redirect.INHERIT);
            ffVideoBuilder.redirectError(Redirect.INHERIT);
            ffAudioBuilder.redirectError(Redirect.INHERIT);
            //Start the video process
            videoProc = videoBuilder.start();
            //Wait for the video process to finish
            videoProc.waitFor();
            //The video generating process is done, start generating the audio
            audioProc = audioBuilder.start();
            //wait for the audio to be generated
            audioProc.waitFor();
            //We have both the audio video, and the final (which was generated at the end of the audio script
            //Now, we can start ffplay
            //We don't need to wait for it though, because we want the user to be able to minimize the java window, and if we waited, they couldn't
            //Start the audio and video as seperate processes
            ffVideoProc = ffVideoBuilder.start();
            ffAudioProc = ffAudioBuilder.start();
        } catch (IOException ex) {
            //There was an error, print it
            System.out.println(ex.toString());
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }
        //Set the stopPlayback button to visible so you can click it and stop ffplay
        this.dStopButton.setVisible(true);
    }//GEN-LAST:event_PlayMedia

    private void StopPlayback(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopPlayback
        //Destroy the proccess that's playing ffmpeg
        ffVideoProc.destroy();
        ffAudioProc.destroy();
        //Make the stop button invisible since nothing's playing anymore
        this.dStopButton.setVisible(false);
    }//GEN-LAST:event_StopPlayback

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ampGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ampGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ampGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ampGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ampGUI().setVisible(true);
            }
        });
    }
    
    public List<File> generatePlayList(boolean type, File listDir){
        
        //Eventually, we'll be able to differentiate between the music files and the video files
        if(type == true) System.out.println("List type: Music");
        else System.out.println("List type: Video. Not yet supported, generating music list");
        
        //Create a blank list for all the paths and songs
        List<File> myFileList;

        //Only add the songs to the list if we're in a directory
        if(listDir.isDirectory()){
            //
            myFileList = getFiles(listDir);
            System.out.println(myFileList.toString());
        } else {
            //We're not in a directory, so we can't do anything
            System.out.println("Selected directory is not a directory");
            myFileList = null;
        }

        
        return myFileList;
    }
    
    //Get all files of type mp3s in a directory
    public List<File> getFiles(File dirName){
        
        //Return a list of files in the passed directory after it gets filtered
        return Arrays.asList(dirName.listFiles(new FilenameFilter() { 
                //Determine whether or not to accept the file
                 public boolean accept(File dir, String filename) {
                     //Return the file if it ends with mp3
                     return filename.endsWith(".mp3");
                 }
        } ));

    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dBoxMusicDir;
    private javax.swing.JButton dButtonSelectDir;
    private javax.swing.JLabel dLabelMusicDir;
    private javax.swing.JPanel dPanelMain;
    private javax.swing.JButton dStartButton;
    private javax.swing.JButton dStopButton;
    private javax.swing.JPanel dTitlePanel;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
