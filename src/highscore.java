import java.io.*;
import java.util.Scanner;


public class highscore {
	
	public player[] players = new player[5];

	public highscore() {

		try {
			
			File fin = new File("highscore.txt");
			Scanner file = new Scanner(fin);
			
			for (int i=0; i<5; i++) {
				
				String name;
				long score;
				
				if (file.hasNext()) {
					name = file.next();
					if (!file.hasNextLong())
						name += (" " + file.next());
					score = file.nextLong();

					if (file.hasNextLine())
						file.nextLine();
				}
				else {
					name = ("----- --------");
					score = 0;
				}
				
				players[i] = new player(name, score);
			
			}
			file.close();
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String getName(int player) {
		return players[player].name;
	}
	
	public long getScore(int player) {
		return players[player].score;
	}
	
	public int setHighscore(String name, long score) {
		
		int i = 4;
		
		try {
			
			PrintWriter out = new PrintWriter(new FileWriter("highscore.txt"));
			
			long lastLong;
			String lastName;
			System.out.println(name);
			while (score > players[i].score && i > 0) {
				System.out.println("score " + score + " > players[" + i + "].score " + players[i].score);
				i--;
			}
			lastLong = players[i].score;
			lastName = players[i].name;
			players[i].score = score;
			players[i].name = name;
			for (int j=i+1; j<3; j++) {
				players[j].score = lastLong;
				players[j].name = lastName;
				lastLong = players[j+1].score;
				lastName = players[j+1].name;
			}
			
			for (int j=0; j<5; j++) {
				out.println(players[j].name + " " + players[j].score);
			}
				
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
}
