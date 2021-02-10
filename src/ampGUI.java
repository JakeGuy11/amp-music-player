//Imports
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.util.List;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author Jake_Guy_11
 */
public class ampGUI extends javax.swing.JFrame {
    
    //Global Paths
    private Path musicPath = Paths.get(System.getProperty("user.home") + "/Music");
    private Path videoPath = Paths.get(System.getProperty("user.dir") + "/media");
    private Path homePath = Paths.get(System.getProperty("user.home"));
    //The process builder for copy process
    ProcessBuilder basicBuilder;
    //The process builder for the video generating script
    ProcessBuilder videoBuilder;
    //The process builder for the audio generating script
    ProcessBuilder audioBuilder;
    //The process builder for the ffplay video
    ProcessBuilder ffVideoBuilder;
    //The process builder for the ffplay audio
    ProcessBuilder ffAudioBuilder;
    //The process builder for the script to join the ffplay 'lives'
    ProcessBuilder joinProcessesBuilder;
    //The actual process for the copy command
    Process basicProc;
    //The actual process for the video generating script
    Process videoProc;
    //The actual process for the audio generating script
    Process audioProc;
    //The actual process for the ffplay video
    Process ffVideoProc;
    //The actual process for the ffplay audio
    Process ffAudioProc;
    //The actual process for the script to join the ffplay 'lives'
    Process joinProcessesProc;
    
    //Create JForm GUI
    public ampGUI() {
        //Do the built-in init
        initComponents();
        //Override the default close, to make sure all processes are closed
        addWindowListener(new WindowAdapter() {
            //This is called when the window is closed
            public void windowClosing(WindowEvent e) {
                //Destroy all 4 processes if they exist and are not null
                try{
                    if(videoProc != null){
                        if(videoProc.isAlive()) videoProc.destroy();
                        if(audioProc.isAlive()) audioProc.destroy();
                        if(ffVideoProc.isAlive()) ffVideoProc.destroy();
                        if(ffAudioProc.isAlive()) ffAudioProc.destroy();
                    }
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
                
                System.exit(0);
            }
        });
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

    //Auto-generated code. DO NOT MODIFY UNLESS YOU WANT TO
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dVideoButtonGroup = new javax.swing.ButtonGroup();
        dPanelMain = new javax.swing.JPanel();
        dTitlePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dLabelMusicDir = new javax.swing.JLabel();
        dBoxMusicDir = new javax.swing.JTextField();
        dButtonSelectDir = new javax.swing.JButton();
        dStartButton = new javax.swing.JButton();
        dStopButton = new javax.swing.JButton();
        dCleanMode = new javax.swing.JCheckBox();
        dConvertMode = new javax.swing.JCheckBox();
        dVideoOptionsLabel = new javax.swing.JLabel();
        dDefaultVideoButton = new javax.swing.JRadioButton();
        dYoutubeVideoButton = new javax.swing.JRadioButton();
        dVideoURL = new javax.swing.JTextField();
        dIsLivestream = new javax.swing.JCheckBox();
        dVideoHelp = new javax.swing.JButton();
        dAudioHelp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        dTitlePanel.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html><h2>AMP Music Player</h1></html>");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
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

        dCleanMode.setText("Clean Console Mode");

        dConvertMode.setText("Convert non-native video formats");

        dVideoOptionsLabel.setText("Select your video options:");

        dVideoButtonGroup.add(dDefaultVideoButton);
        dDefaultVideoButton.setSelected(true);
        dDefaultVideoButton.setText("Use the default video location");

        dVideoButtonGroup.add(dYoutubeVideoButton);
        dYoutubeVideoButton.setText("Use YouTube video");
        dYoutubeVideoButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                VideoButtonsChanged(evt);
            }
        });

        dVideoURL.setText("https://www.youtube.com/watch?v=");
        dVideoURL.setEnabled(false);

        dIsLivestream.setText("Livestream");

        dVideoHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/help.gif"))); // NOI18N
        dVideoHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VideoHelpClicked(evt);
            }
        });

        dAudioHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/help.gif"))); // NOI18N
        dAudioHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AudioHelpClicked(evt);
            }
        });

        javax.swing.GroupLayout dPanelMainLayout = new javax.swing.GroupLayout(dPanelMain);
        dPanelMain.setLayout(dPanelMainLayout);
        dPanelMainLayout.setHorizontalGroup(
            dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dTitlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(dPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dPanelMainLayout.createSequentialGroup()
                        .addComponent(dConvertMode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dStopButton)
                        .addGap(18, 18, 18)
                        .addComponent(dStartButton))
                    .addGroup(dPanelMainLayout.createSequentialGroup()
                        .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dBoxMusicDir)
                            .addGroup(dPanelMainLayout.createSequentialGroup()
                                .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dPanelMainLayout.createSequentialGroup()
                                        .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dLabelMusicDir)
                                            .addGroup(dPanelMainLayout.createSequentialGroup()
                                                .addComponent(dVideoOptionsLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(dVideoHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(dDefaultVideoButton)
                                            .addComponent(dYoutubeVideoButton)
                                            .addComponent(dCleanMode))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dAudioHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(dPanelMainLayout.createSequentialGroup()
                                        .addComponent(dVideoURL, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dIsLivestream)))
                                .addGap(0, 3, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dButtonSelectDir)))
                .addContainerGap())
        );
        dPanelMainLayout.setVerticalGroup(
            dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dPanelMainLayout.createSequentialGroup()
                .addComponent(dTitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dLabelMusicDir)
                    .addComponent(dAudioHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dBoxMusicDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dButtonSelectDir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dVideoOptionsLabel)
                    .addComponent(dVideoHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dDefaultVideoButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dYoutubeVideoButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dVideoURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dIsLivestream))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(dCleanMode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dStartButton)
                    .addComponent(dStopButton)
                    .addComponent(dConvertMode))
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
            .addComponent(dPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //The ... button was selected, let them choose a directory
    private void SelectDirSelected(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectDirSelected
        //Create a new instance of a file choose
        JFileChooser fileChooser = new JFileChooser();
        //Set the starting directory to the home directory
        fileChooser.setCurrentDirectory(new File(musicPath.toUri()));
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
    
    //TODO: Replace the individual character replacements with all non-alphanumeric character replacement
    //Above: If 2 files end up with the same name, just append the system uptime or smth
    
    //The play button was selected
    private void PlayMedia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayMedia
        boolean waitMode = this.dCleanMode.isSelected();
        
        System.out.println("Killing old processes");
        
        //If any of our processes are already opened, kill them
        try{
            if(videoProc != null){
                if(basicProc.isAlive()) basicProc.destroy();
                if(videoProc.isAlive()) videoProc.destroy();
                if(audioProc.isAlive()) audioProc.destroy();
                if(ffVideoProc.isAlive()) ffVideoProc.destroy();
                if(ffAudioProc.isAlive()) ffAudioProc.destroy();
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } 
        
        System.out.println("Killed old processes");
        System.out.println("Removing all old mp3's");
        
        //Update our audio library
        //Start out by removing all mp3s in the folder
        //Create a blank string array which we will overwrite with our arguments later
        String[] basicArgs = new String[] {"/bin/bash","-c",""};
        //Set the command to remove all the mp3s the project folder
        basicArgs[2] ="find . -name \"*.mp3\" -type f -delete";
        try {
            //Create a new processBuilder with our rm command
            basicBuilder = new ProcessBuilder(basicArgs);
            //Redirect error and console output
            basicBuilder.redirectOutput(Redirect.INHERIT);
            basicBuilder.redirectError(Redirect.INHERIT);
            //Start the process
            basicProc = basicBuilder.start();
            //Wait for all the files to be removed
            basicProc.waitFor();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }
        
        System.out.println("Removed all old mp3's");
        //Check if we want to convert non-ts videos
        if(this.dConvertMode.isSelected()) {
            System.out.println("Converting non-ts formats");
            int confirmResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to convert non-native video formats?\nThis will likely take a long time and use a lot of system resources.\nCurrently, the only supported types are mkv and mp4.",
                    "Are you sure?",
                    JOptionPane.YES_NO_OPTION);
            if(confirmResult == JOptionPane.YES_OPTION){
                //Now convert non-ts videos to ts
                //Create blank list for all mp4's
                List<File> mp4List;
                //Generate the playlist with all mp4s
                mp4List = generatePlayList("mp4", videoPath.toFile());
                //Create a list for the video parts
                List<String> videoPathPartList;
                //Create a blank string for our file's actual name with .ts
                String currentVideoName = "";
                for(File currentFile : mp4List){
                    try {
                        //Create a list of all the parts of the video's path
                        videoPathPartList = Arrays.asList(currentFile.getAbsoluteFile().toString().split("/"));
                        System.out.println("Video Path Parts: " + videoPathPartList.toString());
                        //Get the last element of that list (the filename) and replace the ext with .ts
                        currentVideoName = videoPathPartList.get(videoPathPartList.size() - 1).replaceAll(".mp4", ".ts");
                        System.out.println(currentVideoName);
                        //Create our arguments for replacing the video with the ts and deleting the old one
                        basicArgs[2] = "ffmpeg -i " + currentFile.getAbsolutePath() + " -s 1920x1080 -q:v 1 ./media/" + currentVideoName + "; " + "rm " + currentFile.getAbsolutePath();
                        //Recreate our basic builder
                        basicBuilder = new ProcessBuilder(basicArgs);
                        //Redirect error and console output
                        basicBuilder.redirectOutput(Redirect.INHERIT);
                        basicBuilder.redirectError(Redirect.INHERIT);
                        //Start the new process
                        basicProc = basicBuilder.start();
                        basicProc.waitFor();
                    } catch (IOException | InterruptedException ex) {
                        System.out.println(ex.toString());
                    }
                }
                //Now do all of the above but for mkv's
                //Create blank list for all mkv's
                List<File> mkvList;
                //Generate the playlist with all mp4s
                mkvList = generatePlayList("mkv", videoPath.toFile());
                for(File currentFile : mkvList){
                    try {
                        //Create a list of all the parts of the video's path
                        videoPathPartList = Arrays.asList(currentFile.getAbsoluteFile().toString().split("/"));
                        System.out.println("Video Path Parts: " + videoPathPartList.toString());
                        //Get the last element of that list (the filename) and replace the ext with .ts
                        currentVideoName = videoPathPartList.get(videoPathPartList.size() - 1).replaceAll(".mkv", ".ts");
                        System.out.println(currentVideoName);
                        //Create our arguments for replacing the video with the ts and deleting the old one
                        basicArgs[2] = "ffmpeg -i " + currentFile.getAbsolutePath() + " -s 1920x1080 -q:v 1 ./media/" + currentVideoName + "; " + "rm " + currentFile.getAbsolutePath();
                        //Recreate our basic builder
                        basicBuilder = new ProcessBuilder(basicArgs);
                        //Redirect error and console output
                        basicBuilder.redirectOutput(Redirect.INHERIT);
                        basicBuilder.redirectError(Redirect.INHERIT);
                        //Start the new process
                        basicProc = basicBuilder.start();
                        basicProc.waitFor();
                    } catch (IOException | InterruptedException ex) {
                        System.out.println(ex.toString());
                    }
                }
            }
            System.out.println("Non-ts formats converted");
        }
        System.out.println("Copying all selected music to ./media");
        
        //Create a list for our audio
        List<File> audioList;
        //Now add the auto-fetched paths to it
        audioList = generatePlayList("mp3", musicPath.toFile());
        //Make a for loop to copy each mp3 file to our media directory
        for (File currentFile : audioList) {
            try {
                //Overwrite the arguments to copy mp3s from our music directory
                basicArgs[2] ="cp \"" + currentFile.getAbsolutePath() + "\" ./media/";
                //overwrite the basicBuilder with our new args
                basicBuilder = new ProcessBuilder(basicArgs);
                //Redirect error and console output
                basicBuilder.redirectOutput(Redirect.INHERIT);
                basicBuilder.redirectError(Redirect.INHERIT);
                //Start the new process
                basicProc = basicBuilder.start();
                basicProc.waitFor();
                System.out.println("Copied: " + currentFile.getAbsolutePath());
            } catch (IOException ex) {
                System.out.println(ex.toString());
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
        }
        
        System.out.println("Copied all music to ./media");
        System.out.println("Starting character replacement script");
        
        //Overwrite all spaces in the file names with underscores (so bash can understand them
        try {
            //Overwrite the arguments to run the replace script
            if(waitMode) basicArgs[2] ="./scripts/characterReplacement.sh";
            else basicArgs[2] ="./scripts/QcharacterReplacement.sh";

            //overwrite the basicBuilder with our new args
            basicBuilder = new ProcessBuilder(basicArgs);
            //Redirect error and console output
            basicBuilder.redirectOutput(Redirect.INHERIT);
            basicBuilder.redirectError(Redirect.INHERIT);
            //Start the new process
            basicProc = basicBuilder.start();
            basicProc.waitFor();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }
        
        System.out.println("Finished character replacement script");
        System.out.println("Adding all the file names (minus the paths) to list");
        
        //Get the file names of all our files (since stupid concat can't handle paths)
        //Create an empty list for all our file basenames
        List<String> audioNameList = new ArrayList<>();
        //Create an empty list for all the directories in the path that we'll split
        List<String> audioPathPartList = new ArrayList<>();
        //Create a string to hold the name value while we replace the problematic characters
        String currentAudioName = "";
        //Create a for loop for each of our files in the audioList
        for(File currentFile : audioList){
            //Make our path parts list equal to an array of the entire path split at each "/"
            audioPathPartList = Arrays.asList(currentFile.getAbsoluteFile().toString().split("/"));
            //Replace all problematic characters with _ so our script doesn't think they're seperate arguments
            //THESE MUST MATCH THE CHARACTERS IN THE BASH SCRIPT OR IT WILL NOT WORK
            currentAudioName = audioPathPartList.get(audioPathPartList.size() - 1).replaceAll(" ", "_");
            currentAudioName = currentAudioName.replaceAll("\\(", "_");
            currentAudioName = currentAudioName.replaceAll("\\)", "_");
            currentAudioName = currentAudioName.replaceAll("'", "_");
            audioNameList.add(currentAudioName);
        }        
        
        System.out.println("Finished adding all the file names to list");
        System.out.println("Adding all video to list");
        
        //Create a list of all the video (ts) files
        List<File> videoList;
        videoList = generatePlayList("ts", videoPath.toFile());
        
        //Get the file names of all our files (since stupid concat can't handle paths)
        //Create an empty list for all our file basenames
        List<String> videoNameList = new ArrayList<>();
        //Create an empty list for all the directories in the path that we'll split
        List<String> videoPathPartList = new ArrayList<>();
        //Create a string to hold the name value while we replace the problematic characters
        String currentVideoName = "";
        //Create a for loop for each of our files in the audioList
        for(File currentFile : videoList){
            //Make our path parts list equal to an array of the entire path split at each "/"
            videoPathPartList = Arrays.asList(currentFile.getAbsoluteFile().toString().split("/"));
            //Add the actual file to the list of file names
            //We don't do character replacement because the video will eventually be provided by the server, so we'll know there are no problematic characters
            currentVideoName = videoPathPartList.get(videoPathPartList.size() - 1);
            //Add our file name to our video name list
            videoNameList.add(currentVideoName);
        }        
        
        System.out.println("Added all video to list");
        System.out.println("Generating audio concat command");
        
        //Now we need to generate the string that concats and runs our audio
        //Randomize the play order
        Collections.shuffle(audioNameList);
        //Create a string joiner that will put our array 
        StringJoiner strJoiner = new StringJoiner(" ", "", "");
        //Add each element of our file name array to our string joiner
        audioNameList.forEach(strJoiner::add);
        //Print the complete argument string
        String audioListToInsert = strJoiner.toString();
        
        System.out.println("Generated audio concat command: " + audioListToInsert);
        System.out.println("Generating video concat command");
        
        //Now we need to generate the string that concats our video
        //Randomize the play order
        Collections.shuffle(videoNameList);
        //Recreate our string joiner so we don't have all the old entries in it
        strJoiner = new StringJoiner(" ", "", "");
        //Add each element of our file name array to our string joiner
        videoNameList.forEach(strJoiner::add);
        //Add the complete argument string to the video list string
        String videoListToInsert = strJoiner.toString();
        
        System.out.println("Generated video concat command: " + videoListToInsert);
        System.out.println("Generating script commands and arguments");
        
        //Create a string array with all the parts of the ffplay video command
        //This will be replaced with the generatePlayList method eventually, at least for the arguments
        //For now, vid1.ts and vid2.ts are just funny clips, but will be replaced in the future
        String[] videoArgs = new String[] {"/bin/bash","-c",""};
        if(waitMode) videoArgs[2] = "./scripts/runVideo.sh " + videoListToInsert;
        else videoArgs[2] = "./scripts/QrunVideo.sh " + videoListToInsert;
        //Create a string array with the names of the audio we want to play
        String[] audioArgs = new String[] {"/bin/bash","-c",""};
        if(waitMode) audioArgs[2] = "./scripts/runAudio.sh " + audioListToInsert;
        else audioArgs[2] = "./scripts/QrunAudio.sh " + audioListToInsert;
        //Create a string array with all the commands for the ffplay command. This will not be changed dynamically
        String[] ffVideoArgs = new String[] {"/bin/bash","-c","ffplay -fs -loop -1 ./gen/videoOut.mp4"};
        String[] ffAudioArgs = new String[] {"/bin/bash","-c","ffplay -nodisp -loop -1 ./gen/audioOut.mp3"};
        
        System.out.println("Generated script commands and arguments");
        System.out.println("Starting all processes");
        
        //Make a variable on whether or not to get the video from YouTube or not
        boolean isCloudVideo = this.dYoutubeVideoButton.isSelected();
        boolean isLivestream = this.dIsLivestream.isSelected();
        
        //If it is a cloud video, change the ffplay command to play it
        if(isCloudVideo) ffVideoArgs[2] = "ffplay -fs -loop -1 -an `youtube-dl -f best -g " + this.dVideoURL.getText() + "`";
        if(isLivestream) ffVideoArgs[2] = "ffplay -fs -an `youtube-dl -f best -g " + this.dVideoURL.getText() + "`";
        
        try {
            System.out.println("Creating processBuilders");
            //Create a new processbuilder made from the parts of the command we want to run for all 4 processes
            videoBuilder = new ProcessBuilder(videoArgs);
            audioBuilder = new ProcessBuilder(audioArgs);
            ffVideoBuilder = new ProcessBuilder(ffVideoArgs);
            ffAudioBuilder = new ProcessBuilder(ffAudioArgs);
            System.out.println("Created processBuilders");
            //Redirect any terminal output to the default location, in this case that's the console for all 4 processes
            System.out.println("Redirecting terminal output");
            videoBuilder.redirectOutput(Redirect.INHERIT);
            audioBuilder.redirectOutput(Redirect.INHERIT);
            ffVideoBuilder.redirectOutput(Redirect.INHERIT);
            ffAudioBuilder.redirectOutput(Redirect.INHERIT);
            //Redirect any terminal errors to the default location, in this case that's the console for all 4 processes
            videoBuilder.redirectError(Redirect.INHERIT);
            audioBuilder.redirectError(Redirect.INHERIT);
            ffVideoBuilder.redirectError(Redirect.INHERIT);
            ffAudioBuilder.redirectError(Redirect.INHERIT);
            System.out.println("Redirected terminal output");
            if(!isCloudVideo){
                System.out.println("Starting video process");
                videoProc = videoBuilder.start();
                //Wait for the video process to finish
                videoProc.waitFor();
                TimeUnit.SECONDS.sleep((long) 0.5);
                System.out.println("Video proc finished");
            }
            //The video generating process is done, start generating the audio
            System.out.println("Starting audio process");
            audioProc = audioBuilder.start();
            //wait for the audio to be generated
            audioProc.waitFor();
            TimeUnit.SECONDS.sleep((long) 0.5);
            System.out.println("Audio process finished");
            
            //We have both the audio video, and the final (which was generated at the end of the audio script
            //Now, we can start ffplay
            //We don't need to wait for it though, because we want the user to be able to minimize the java window, and if we waited, they couldn't
            //Start the audio and video as seperate processes
            System.out.println("Starting ffplay scripts");
            ffVideoProc = ffVideoBuilder.start();
            ffAudioProc = ffAudioBuilder.start();
            System.out.println("Started ffplay scripts");
            
            //Now that both the audio and video are going, start the script that will join the 'existences' of the two ffplays (since 1 can be closed manually, make it so it can kill the other)
            //Create the command
            String[] joinProcessesArgs = new String[] {"/bin/bash","-c",""};
            if(waitMode) joinProcessesArgs[2] = "./scripts/connectFFProcesses.sh " + ffVideoProc.pid() + " " + ffAudioProc.pid();
            else joinProcessesArgs[2] = "./scripts/QconnectFFProcesses.sh " + ffVideoProc.pid() + " " + ffAudioProc.pid();
            //Create a new processBuilder from the arguments above
            joinProcessesBuilder = new ProcessBuilder(joinProcessesArgs);
            //Redirect the console outputs
            joinProcessesBuilder.redirectOutput(Redirect.INHERIT);
            //Redirect the console errors
            joinProcessesBuilder.redirectError(Redirect.INHERIT);
            //Start the process
            joinProcessesProc = joinProcessesBuilder.start();
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

    private void VideoButtonsChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_VideoButtonsChanged
        //Someone changed the radio button selection
        //If 'custom video' is selected, enable the ext field so they can put their url in
        if(this.dYoutubeVideoButton.isSelected()) {
            //The custom video option is selected. Enable the text field
            this.dVideoURL.setEnabled(true);
        } else {
            //The custom video option is not selected. Disable the text field
            this.dVideoURL.setEnabled(false);
        }
    }//GEN-LAST:event_VideoButtonsChanged

    private void VideoHelpClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VideoHelpClicked
        //Display a dialogue with more video information
        JOptionPane.showMessageDialog(this, "If the \"Use the default video location\"\nis selected, all compatable videos in\n~/.amp/media/ will be played. If the\n\"Use YouTube video\" option is selected,\nthe video URL in the text box will be played.",
                "Video Help",
                JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_VideoHelpClicked

    private void AudioHelpClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AudioHelpClicked
        JOptionPane.showMessageDialog(this, "All the audio in the entered directory will be\nplayed. However, audio files in nested\ndirectories will not be played.",
                "Audio Help",
                JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_AudioHelpClicked

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
    
    public List<File> generatePlayList(String ext, File listDir){
        
        //Create a blank list for all the paths and files
        List<File> myFileList;
        //Only add the files to the list if we're in a directory
        if(listDir.isDirectory()){
            myFileList = getFiles(listDir, ext);
            System.out.println("Auto-gen array: " + myFileList.toString());
        } else {
            //We're not in a directory, so we can't do anything
            System.out.println("Selected directory is not a directory");
            myFileList = null;
        }

        
        return myFileList;
    }
    
    //Get all files of type mp3s in a directory
    public List<File> getFiles(File dirName, String ext){
        
        //Return a list of files in the passed directory after it gets filtered
        return Arrays.asList(dirName.listFiles(new FilenameFilter() { 
                //Determine whether or not to accept the file
                 public boolean accept(File dir, String filename) {
                     //Return the file if it ends with mp3
                     return filename.endsWith("." + ext);
                 }
        } ));

    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dAudioHelp;
    private javax.swing.JTextField dBoxMusicDir;
    private javax.swing.JButton dButtonSelectDir;
    private javax.swing.JCheckBox dCleanMode;
    private javax.swing.JCheckBox dConvertMode;
    private javax.swing.JRadioButton dDefaultVideoButton;
    private javax.swing.JCheckBox dIsLivestream;
    private javax.swing.JLabel dLabelMusicDir;
    private javax.swing.JPanel dPanelMain;
    private javax.swing.JButton dStartButton;
    private javax.swing.JButton dStopButton;
    private javax.swing.JPanel dTitlePanel;
    private javax.swing.ButtonGroup dVideoButtonGroup;
    private javax.swing.JButton dVideoHelp;
    private javax.swing.JLabel dVideoOptionsLabel;
    private javax.swing.JTextField dVideoURL;
    private javax.swing.JRadioButton dYoutubeVideoButton;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
