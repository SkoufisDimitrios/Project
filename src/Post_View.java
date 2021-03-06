
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;



public class Post_View extends JPanel {
	
	private Post aPost;

	private JPanel postPanel = new JPanel();
	private JScrollPane scrollpane;
	private JTextPane txtComment;
	private JFrame frame1;
	private JPanel groupallPanel = new JPanel();
	private JPanel createpost = new JPanel();
	private JButton Post1;
	private JTextField postfield;
	private JButton Comment;
	private JButton Like;
	private User activeUser;
	private User anotherUser=null;
	private Group agroup=null;
	
	
	public Post_View(User activeUser, User anotherUser){//Used for displaying posts on User_Timeline
		
		int i;
		this.activeUser = activeUser;
		this.anotherUser = anotherUser;

		postPanel.setLayout(new BoxLayout(postPanel,BoxLayout.Y_AXIS));


		groupallPanel = new JPanel();
		groupallPanel.setLayout(new BoxLayout(groupallPanel,BoxLayout.Y_AXIS));
			
		postToBeDisplayedUser(anotherUser);
		groupallPanel.add(postPanel);
		add(groupallPanel);
	}
	
	
	public Post_View(User activeUser, Group agroup){ //Used for displaying posts on Group_Timeline
		this.activeUser = activeUser;
		this.agroup = agroup;	
		int i;

		postPanel.setLayout(new BoxLayout(postPanel,BoxLayout.Y_AXIS));

		groupallPanel = new JPanel();
		groupallPanel.setLayout(new BoxLayout(groupallPanel,BoxLayout.Y_AXIS));

		postToBeDisplayedGroup(agroup);
		groupallPanel.add(postPanel);
		add(groupallPanel);
	}
	
	public Post_View(User activeUser){ //Used for displaying posts on Home_Page
		this.activeUser = activeUser;
		
		postPanel.setLayout(new BoxLayout(postPanel,BoxLayout.Y_AXIS));

		groupallPanel = new JPanel();
		groupallPanel.setLayout(new BoxLayout(groupallPanel,BoxLayout.Y_AXIS));

		postToBeDisplayedHomePage();
		groupallPanel.add(postPanel);
		add(groupallPanel);
		
	}

	public JPanel aPostView(final Post aPost){
		// User for displaying a specific post on User_Timeline and Group_Timeline
		this.aPost = aPost;
		JPanel apanel = new JPanel();
		
		apanel.setLayout(new BorderLayout(0, 0));
		JLabel alabel = new JLabel(aPost.getUser().getName() + ": " +aPost.getPostText());
		alabel.setHorizontalAlignment(SwingConstants.CENTER);
		apanel.add(alabel, BorderLayout.NORTH);
		
		JButton likebutton = new JButton("Like");
		apanel.add(likebutton, BorderLayout.CENTER);
		
		JButton commentbutton = new JButton("Comment");
		apanel.add(commentbutton, BorderLayout.EAST);
		
		final JLabel likeslabel;
		if (aPost.NumberOfLikes()>0){
			likeslabel = new JLabel(aPost.NumberOfLikes() + "");
		}
		else
			likeslabel = new JLabel("0");
		apanel.add(likeslabel, BorderLayout.WEST);
		
		likebutton.addActionListener(new ActionListener(){				
			public void actionPerformed(ActionEvent e)
			{	
				aPost.addLike(activeUser);
				likeslabel.setText(aPost.NumberOfLikes() + "");
				DataBase.save();
			}
		});
		commentbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	
				int i;
				frame1 = new JFrame("Post");
				frame1.setVisible(true);
				frame1.setBounds(12, 66, 662, 429);
				frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame1.getContentPane().setLayout(null);

				JLabel lblPost = new JLabel(aPost.getUser().getName()+ " : " +aPost.getPostText());
				lblPost.setHorizontalAlignment(SwingConstants.CENTER);
				lblPost.setFont(new Font("Arial", Font.PLAIN, 18));
				lblPost.setBounds(12, 13, 620, 51);
				frame1.getContentPane().add(lblPost);

				JPanel panel1 = new JPanel();
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
				scrollpane = new JScrollPane(panel1,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollpane.setBounds(12, 72, 620, 192);
				scrollpane.setBorder(null);

				if(aPost.getReplies().size()>=1){
					panel.removeAll();
					for(i=aPost.getReplies().size();i>0;i--){ // gia na emfanizetai to teleutaio post pou dimiourgithike prwto
						panel.add(aPostView(aPost.getReplies().get(i-1)));
					}
				}
				panel1.add(panel);
				frame1.getContentPane().add(scrollpane);


				JButton btnComment = new JButton("Comment");
				btnComment.setFont(new Font("Arial", Font.PLAIN, 16));
				btnComment.setBounds(246, 344, 152, 25);
				btnComment.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame1.dispose();
						DataBase.createPostFrame(activeUser, null, null, aPost);
					}
				});
				frame1.getContentPane().add(btnComment);
				
				JButton btnBack = new JButton("Back");
				btnBack.setFont(new Font("Arial", Font.PLAIN, 16));
				btnBack.setBounds(535, 340, 97, 25);
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						frame1.dispose();
					}
				});
				frame1.getContentPane().add(btnBack);
			}
		});

		JPanel actions = new JPanel();

		actions.add(likebutton);
		actions.add(commentbutton);
		apanel.add(actions, BorderLayout.CENTER);

		this.setSize(getPreferredSize());
		return apanel;
	}
	
	public JPanel aPostViewHomePage(final Post aPost){
		//Used for displaying a specific post on Home_Page
		this.aPost = aPost;
		JPanel apanel = new JPanel();
		
		apanel.setLayout(new BorderLayout(0, 0));
		
		String labelname="";
		if(aPost.getOwner()!=null){
			labelname = aPost.getUser().getName() + " to "+ aPost.getOwner().getName() + " : "  +aPost.getPostText();
		}
		else if(aPost.getGroup()!=null){
			labelname = aPost.getUser().getName() + " to "+ aPost.getGroup().getName() + " : "  +aPost.getPostText();
		}
		
		JLabel alabel = new JLabel(labelname);
		alabel.setHorizontalAlignment(SwingConstants.CENTER);
		apanel.add(alabel, BorderLayout.NORTH);
		
		JButton likebutton = new JButton("Like");
		apanel.add(likebutton, BorderLayout.CENTER);
		
		JButton commentbutton = new JButton("Comment");
		apanel.add(commentbutton, BorderLayout.EAST);
		
		final JLabel likeslabel;
		if (aPost.NumberOfLikes()>0){
			likeslabel = new JLabel(aPost.NumberOfLikes() + "");
		}
		else
			likeslabel = new JLabel("0");
		apanel.add(likeslabel, BorderLayout.WEST);


			
		likebutton.addActionListener(new ActionListener(){				
				public void actionPerformed(ActionEvent e)
				{	
					aPost.addLike(activeUser);
					likeslabel.setText(aPost.NumberOfLikes()+ "");
					DataBase.save();
				}	
		});
			
		commentbutton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){	
					int i;
					frame1 = new JFrame("Post");
					frame1.setVisible(true);
					frame1.setBounds(12, 66, 662, 429);
					frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame1.getContentPane().setLayout(null);

					JLabel lblPost = new JLabel(aPost.getUser().getName()+ " : " +aPost.getPostText());
					lblPost.setHorizontalAlignment(SwingConstants.CENTER);
					lblPost.setFont(new Font("Arial", Font.PLAIN, 18));
					lblPost.setBounds(12, 13, 620, 51);
					frame1.getContentPane().add(lblPost);

					JPanel panel1 = new JPanel();
					JPanel panel = new JPanel();
					panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
					scrollpane = new JScrollPane(panel1,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					scrollpane.setBounds(12, 72, 620, 192);
					scrollpane.setBorder(null);

					if(aPost.getReplies().size()>=1){
						panel.removeAll();
						for(i=aPost.getReplies().size();i>0;i--){ // gia na emfanizetai to teleutaio post pou dimiourgithike prwto
							panel.add(aPostView(aPost.getReplies().get(i-1)));
						}
					}
					panel1.add(panel);
					frame1.getContentPane().add(scrollpane);


					JButton btnComment = new JButton("Comment");
					btnComment.setFont(new Font("Arial", Font.PLAIN, 16));
					btnComment.setBounds(246, 344, 152, 25);
					btnComment.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							frame1.dispose();
							DataBase.createPostFrame(activeUser, null, null, aPost);
						}
					});
					frame1.getContentPane().add(btnComment);
					
					JButton btnBack = new JButton("Back");
					btnBack.setFont(new Font("Arial", Font.PLAIN, 16));
					btnBack.setBounds(535, 340, 97, 25);
					btnBack.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							frame1.dispose();
						}
					});
					frame1.getContentPane().add(btnBack);
				}
			});

			JPanel actions = new JPanel();

			actions.add(likebutton);
			actions.add(commentbutton);
			apanel.add(actions, BorderLayout.CENTER);


			this.setSize(getPreferredSize());
			return apanel;
		}


	public void postToBeDisplayedUser(User anotherUser){
		//Sorts an ArrayList based on Date(for users)
		Collections.sort(anotherUser.getPersonalPosts(), new Comparator<Post>() {
	        public int compare(Post post2, Post post1)
	        {
	            return  post1.getDate().compareTo(post2.getDate());
	        }
	    });
		for(Post post: anotherUser.getPersonalPosts()){
			postPanel.add(aPostView(post));
		}
	}


	public void postToBeDisplayedGroup(Group agroup){ 
		//Sorts an ArrayList based on Date(for groups)
		Collections.sort(agroup.getGroupPosts(), new Comparator<Post>() {
	        public int compare(Post post2, Post post1)
	        {
	            return  post1.getDate().compareTo(post2.getDate());
	        }
	    });
		for(Post post: agroup.getGroupPosts()){
			postPanel.add(aPostView(post));
		}
	}
	
	public void postToBeDisplayedHomePage(){
		//Adds all Posts from all friends and groups that a user has , and then sorts them by date
		List<Post> all = new ArrayList<Post>();
		all.removeAll(all);
		for(int i=0;i<activeUser.getGroups().size();i++){
			for(Post post: activeUser.getGroups().get(i).getGroupPosts()){
				all.add(post);
			}
		}
		for(int i=0;i<activeUser.getFriends().size();i++){
			for(Post post: activeUser.getFriends().get(i).getPersonalPosts()){
				all.add(post);
			}
		}
		Collections.sort(all, new Comparator<Post>() {
	        public int compare(Post post2, Post post1)
	        {
	            return  post1.getDate().compareTo(post2.getDate());
	        }
	    });
		for(Post post: all){
			postPanel.add(aPostViewHomePage(post));
		}
	}

	
	

}