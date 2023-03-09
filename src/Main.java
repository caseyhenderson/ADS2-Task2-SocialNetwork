import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

public class Main extends JFrame {
    private JPanel rootPanel;
    private JTextField userName;
    private JButton LogIn;
    private JList FriendList;
    private JList SuggestedList;
    private JLabel information;
    private JButton randomFriend;
    private JButton addFriend;
    private JLabel RandomFriend;
    private JTextField newFriend;

    private String currentUser;
    private String randomUser;
    private String toAdd;
    private String[] randomUsers;
    private String[] newFriends;
    private int currentID;
    private int newID;
    private SocialNetwork friendsNetwork;

    public Main() throws FileNotFoundException {
        //Load the data
        friendsNetwork = new SocialNetwork();
        friendsNetwork.Load();

        LogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser = userName.getText();
                currentID = friendsNetwork.FindUserID(currentUser);
                if (currentID == -1){
                    information.setText("User Information: Unknown");
                }
                else{
                    information.setText("User Information: User Name:"+currentUser+" User ID:"+ currentID);
                    String[] friends = friendsNetwork.GetMyFriends(currentUser);
                    FriendList.setListData(friends);

                    String[] recommendations = friendsNetwork.GetRecommended(currentUser);
                    SuggestedList.setListData(recommendations);
                }

            }
        });
        randomFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomUsers = new String[friendsNetwork.GetMyFriends(currentUser).length];
                randomUsers = friendsNetwork.GetMyFriends(currentUser);

                //System.out.println(max);
                int occupied =0;
                for (int i = 0; i<randomUsers.length; i++)
                {
                    if(randomUsers[i]!=null)
                    {
                        occupied++;
                    }

                }
                int max = occupied-1;
                int min = 1;
                int range = max-min+1;
                int random = (int) (Math.random()* range) + min;
                System.out.println(random);
                randomUser = randomUsers[random];
                if(randomUser!=null)
                {
                    RandomFriend.setText(randomUser);
                }
                else
                {
                    RandomFriend.setText("not found");
                }

            }
        });
        addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toAdd = newFriend.getText();
                newID = 101;
                // cosmetic only
                //String[] newFriends = new String[friendsNetwork.GetMyFriends(currentUser).length+1];
                newFriends =friendsNetwork.GetMyFriends(currentUser);
                for (int i=0; i<newFriends.length; i++)
                {
                    if(newFriends[i]==null)
                    {
                        newFriends[i]= toAdd;
                        // persist
                        break;
                    }
                }
                FriendList.setListData(newFriends);

            }
        });



        //Clear the input field
        userName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                userName.setText("");
            }
        });
        newFriend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                newFriend.setText("");
            }
        });

    }

    public static void main(String[] args) throws FileNotFoundException {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
