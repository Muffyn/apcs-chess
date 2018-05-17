# apcs-chess

Here is the info from the Word doc:

**Setting up Git
•	Download and install the latest version of GitHub Desktop. This will automatically install Git andkeep it up-to-date for you.
Project leader does:
•	On your computer, open the Git Shell application.
•	Tell Git your name so your commits will be properly labeled.
•	Tell Git the email address that will be associated with your Git commits. The email you specify should be the same one found in your email settings.

Project leader does:
Authenticating with GitHub from Git
When you connect to a GitHub repository from Git, you'll need to authenticate with GitHub using either HTTPS or SSH.
Connecting over HTTPS (recommended)
If you clone with HTTPS, you can cache your GitHub password in Git using a credential helper.

Project leader does:
Create a new repository on GitHub
1.	In the upper-right corner of any page, click , and then click New repository.
2.	Create a short, memorable name for your repository. For example, "hello-world".
3.	Optionally, add a description of your repository. For example, "My first repository on GitHub."
4.	Choose between creating a public or private repository.
o	Public repositories are a great choice for getting started! They're visible to any user on GitHub, so you can benefit from a collaborative community.
o	Private repositories require a little more setup. They're only available to you, the repository owner, as well as any collaborators you choose to share with. Private repositories are only available for paid accounts. For more information, see "What plan should I choose?."
5.	Select Initialize this repository with a README.
6.	Click Create repository.

**Cloning a repository
When you create a repository on GitHub, it exists as a remote repository. You can clone your repository to create a local copy on your computer and sync between the two locations.
This procedure assumes you have already created a repository on GitHub, or have an existing repository owned by someone else you'd like to contribute to.
1.	Online, in GitHub, navigate to the main page of the repository. Copy the URL.  It might already be added to your GitHub if your email was added by the project leader.
2.	In GitHub on your computer under your repository name, click  to copy the clone add the URL for the repository or simply click the repository if it is available.
3.	Change the current working directory to the location where you want the cloned directory to be made. If you do not change the directory, one will be created in your documents on your computer in a folder called GIT.
4.	Press Enter. Your local clone will be created.
**Part II
1.	 Open Eclipse.  Do Not create a new project.  Go to file import.  Choose Git and then projects from Git, Next, Existing Local Repository, Next, browse to where the repository (your clone) was saved, Next, check box to select repository, Next..  

**Committing:

1.	In GitHub on your computer, open the repository – changes tab, select the files you want to commit, click commit, and click sync.


**Updating
1.	While in GitHub with repository showing, click sync.
2.	In Eclipse refresh your project.
**Slack
1.	For group communication messaging app for teams – download app for phone, Iphone or Android – uses email to invite to group.


#################################################################################

format for images of pieces (ClassName_White.png & ClassName_Black.png)

#################################################################################
Piece

   static ArrayList<Position> getPositions(Position position, int direction, int spaces)
      returns an ArrayList of positions given a starting point, a direction (you can use the final static ints given), and the number of spaces 
  
  static Position getPosition(Position position, int direction)
      does the same as above but one returns the nearest position
      
  static ArrayList<Piece> reverse(ArrayList<Piece> pieces)
      reverses (flips) all of the pieces in the ArrayList
  
  static boolean inBounds(Position position)
      checks if the positions is in bounds of the board
      
  Piece(int x, int y)
      constructor given x and y values 
  
  Piece(Position position)
      constructor given a position
      
  Piece reverse() *Must be overridden in subclasses
      returns a reversed (flipped) non-reference Piece object of this
  
  boolean move(Position position, ArrayList<Piece> enemies)
      if this piece can move to the position given then this moves to that position
  
  ArrayList<Position> getPossiblePositions()
      returns reference of the possiblePossible of this (all fields if any must be equal)
  
  void setPossiblePositions(ArrayList<Position> positions)
      sets possiblePositions to positions
  
  abstract ArrayList<Position> setPossiblePositions(ArrayList<Piece> allies, ArrayList<Piece> enemies)
      sets possibleSolutions given allies and enemies and returns a reference of possibleSolutions    
      
