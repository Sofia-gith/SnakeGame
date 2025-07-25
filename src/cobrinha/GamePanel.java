package cobrinha;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements  ActionListener{
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'D';//d = direita, e= esquerda, c= cima, b = baixo
	boolean running = true;
	Timer timer;
	Random random;
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}

	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this); 
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g) {
		
		if(running) {
//			for (int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
//				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//				g.drawLine(0, i*UNIT_SIZE, SCREEN_HEIGHT, i*UNIT_SIZE);
//				
//			}
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			for( int i = 0;  i<bodyParts; i++) {
				if(i==0) {
					g.setColor(Color.GREEN);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}else {
					g.setColor(new Color(45,180,0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD,40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Pontos"+ applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Pontos"+ applesEaten))/2, g.getFont().getSize()) ;
			
		}else {
			gameOver(g);
		}
		
	}
	
	public void newApple() {
		appleX = random.nextInt(( int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt(( int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	
	public void move() {
		for(int i= bodyParts; i>0; i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction) {
		case 'C':
			 y[0]= y[0] - UNIT_SIZE; 
		break;
		case 'B':
			 y[0]= y[0] + UNIT_SIZE; 
		break;
		case 'E':
			 x[0]= x[0] - UNIT_SIZE; 
		break;
		case 'D':
			 x[0]= x[0] + UNIT_SIZE; 
		break;
		}
	}
	
	public void  checkApple() {
		if((x[0]== appleX) && (y[0]== appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
		
	}
	
	public void checkCollisions() {
		//checando se a cabeça colide no corpo
		for(int i = bodyParts;i>0;i--) {
			if((x[0]== x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		//checando se a cabeça colide na borda esquerda
		if(x[0]<0) {
			running = false;
		}
		
		//checando se cabeça bate na borda direita
		
		if(x[0]> SCREEN_WIDTH) {
			running = false;
		}
		//checando se cabeça bate na borda de cima
		if(y[0] <  0) {
			running = false;
		}
		//checando se a cabeça bate na borda de baixo
		if(y[0] >  SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		//pontos
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD,40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Pontos"+ applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Pontos"+ applesEaten))/2, g.getFont().getSize());
		
		//texto gameover
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD,75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'D') {
					direction = 'E';
				}
			break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'E') {
					direction = 'D';
				}
			break;
			case KeyEvent.VK_UP:
				if(direction != 'B') {
					direction = 'C';		
				}
			break;
			case KeyEvent.VK_DOWN:
				if(direction != 'C') {
					direction = 'B';
				}
			break;
			}
		}
	}
	

}
