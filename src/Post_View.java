
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import javax.swing.*;



public class Post_View extends JPanel {
	
	private Post aPost;
	JPanel frame;

	private JPanel postPanel;
	private JPanel createpost;
	private JButton Post1;
	private JButton Post2;
	private JButton Post3;
	private JTextField postfield;
	private JButton Comment;
	private JButton Like;
	private int posts_displayed;
	private User activeUser;
	private User anotherUser;
	private Group aGroup;


	public Post_View(User activeUser, User anotherUser){//Used for displaying posts on User_Timeline
		this.activeUser = activeUser;
		this.anotherUser = anotherUser;

		postPanel = new JPanel();

		createpost = new JPanel();
		Post1 = new JButton("Post");
		postfield = new JTextField(20);
		Post1.addActionListener(new PostListener1());


		createpost.add(postfield, BorderLayout.NORTH);
		createpost.add(Post1, BorderLayout.CENTER);

//		frame.add(createpost);
		add(createpost,BorderLayout.NORTH);

		if(anotherUser.getPersonalPosts().size()>=1){
			//			Collections.sort(anotherUser.getPersonalPosts());
			for(Post post: anotherUser.getPersonalPosts()){
				postPanel.add(aPostView(post));
			}
		}
		
//		frame.add(postPanel);
		add(postPanel,BorderLayout.CENTER);

	}
	
	
	public Post_View(User activeUser, Group agroup){ //Used for displaying posts on Group_Timeline\
		this.activeUser = activeUser;
		this.aGroup = agroup;
		
		postPanel = new JPanel();

		createpost = new JPanel();
		Post1 = new JButton("Post");
		postfield = new JTextField(20);
		Post2.addActionListener(new PostListener2());

		createpost.add(postfield, BorderLayout.NORTH);
		createpost.add(Post1, BorderLayout.CENTER);

		add(createpost,BorderLayout.NORTH);

		if(aGroup.getGroupPosts().size()>=1){
			//			Collections.sort(anotherUser.getPersonalPosts());
			for(Post post: aGroup.getGroupPosts()){
				postPanel.add(aPostView(post));
			}
		}
		
		add(postPanel,BorderLayout.CENTER);
	}
	
	public Post_View(User activeUser){ ////Used for displaying posts on Home_Page

		Post apost = new Post("Lalala", activeUser);
		//		aPostView();

	}






	public JPanel aPostView(Post aPost){
		this.aPost = aPost;
		JPanel apanel = new JPanel();
		JLabel alabel = new JLabel(aPost.getPostText());
//		JLabel likeslabel = new JLabel(aPost.NumberOfLikes() + " ");
		JButton likebutton = new JButton("Like!");
		JButton commentbutton = new JButton("Comment");
		likebutton.addActionListener(new likeButtonListener());
		commentbutton.addActionListener(new commentButtonListener());

		//		postTextfield.setText(apost.getPostText());//TODO used for check


		JPanel actions = new JPanel();

		actions.add(likebutton);
		actions.add(commentbutton);

		apanel.add(alabel,BorderLayout.NORTH);
//		apanel.add(likeslabel);
		apanel.add(actions, BorderLayout.CENTER);


		this.setSize(getPreferredSize());
		return apanel;
	}



	public int postToBeDisplayedUser(User activeUser,User anotherUser){
		int count;
		if(activeUser.isFriend(anotherUser)){
			Collections.sort(anotherUser.getPersonalPosts());
		}

		if(activeUser.isFriend(anotherUser)){									 
			Collections.sort(auser.getPersonalPosts()); //TODO check / Collections.sort refers to List not ArrayList
			return auser.getPersonalPosts().get(posts_displayed++);	
		}
		return null;
	}


	public Post postToBeDisplayedGroup(Group agroup){ 
		if(agroup.isMember(activeUser)){							 
			Collections.sort(agroup.getGroupPosts()); //TODO Collections.sort refers to List not ArrayList
			return agroup.getGroupPosts().get(posts_displayed++);
		}
		return null;
	}


	class PostListener1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			DataBase.createPost(activeUser, anotherUser, null, postfield.getText());
//			DataBase.save();
		}
	}
	
	class PostListener2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			DataBase.createPost(activeUser, anotherUser, null, postfield.getText());
//			DataBase.save();
		}
	}
	
	class PostListener3 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			DataBase.createPost(activeUser, anotherUser, null, postfield.getText());
//			DataBase.save();
		}
	}
	
	class likeButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			aPost.addLike(activeUser);
		}
	}
	
	class commentButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			
		}
	}

}