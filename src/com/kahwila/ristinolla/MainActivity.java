package com.kahwila.ristinolla;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.kahwila.ristinolla.R;

public class MainActivity extends Activity {
	
	private TicTacToeGame mGame;
	
	private Button mBoardButtons[];
	private TextView mInfoTextView;
	private TextView mPlayerOneCount;
	private TextView mTieCount;
	private TextView mPlayerTwoCount;
	private TextView mPlayerOneText;
	private TextView mPlayerTwoText;
 
	private int mPlayerOneCounter = 0;
	private int mTieCounter = 0;
	private int mPlayerTwoCounter = 0;
 
	private boolean mPlayerOneFirst = true;
	private boolean mIsSinglePlayer = false;
	private boolean mIsPlayerOneTurn = true;
	private boolean mGameOver = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
				
        mBoardButtons = new Button[TicTacToeGame.getBOARD_SIZE()];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);
 
        mInfoTextView = (TextView) findViewById(R.id.information);
        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        mPlayerTwoCount = (TextView) findViewById(R.id.androidCount);
        mPlayerOneText = (TextView) findViewById(R.id.human);
        mPlayerTwoText = (TextView) findViewById(R.id.android);
 
        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
 
        mGame = new TicTacToeGame();
 
        startNewGame(
        	getIntent().getExtras().getBoolean("gameType")
        );
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	case R.id.newGame:
    		startNewGame(mIsSinglePlayer);
    		break;
    	case R.id.exitGame:
    		MainActivity.this.finish();
    		break;
    	}
 
    	return true;
    }
    
    // start a new game
    // clears the board and resets all buttons / text
    // sets game over to be false
    private void startNewGame(boolean isSingle)
    {
 
    	this.mIsSinglePlayer = isSingle;
 
    	mGame.clearBoard();
 
    	for (int i = 0; i < mBoardButtons.length; i++)
    	{
    		mBoardButtons[i].setText("");
    		mBoardButtons[i].setEnabled(true);
    		mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
    		mBoardButtons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.blank));
    	}
 
    	if (mIsSinglePlayer) 
    	{
    		mPlayerOneText.setText("Human:"); 
    		mPlayerTwoText.setText("Android:"); 
 
    		if (mPlayerOneFirst) 
        	{
        		mInfoTextView.setText(R.string.first_human);
        		mPlayerOneFirst = false;
        	}
        	else
        	{
        		mInfoTextView.setText(R.string.turn_computer);
        		int move = mGame.getComputerMove();
        		setMove(TicTacToeGame.PLAYER_TWO, move);
        		mPlayerOneFirst = true;
        	}
    	}
    	else 
    	{
    		mPlayerOneText.setText("Player One:"); 
    		mPlayerTwoText.setText("Player Two:"); 
 
    		if (mPlayerOneFirst) 
        	{
        		mInfoTextView.setText(R.string.turn_player_one); 
        		mPlayerOneFirst = false;
        	}
        	else
        	{
        		mInfoTextView.setText(R.string.turn_player_two); 
        		mPlayerOneFirst = true;
        	}
    	}
 
    	mGameOver = false;
    }
    
    private class ButtonClickListener implements View.OnClickListener
    {
    	int location;
 
    	public ButtonClickListener(int location)
    	{
    		this.location = location;
    	}
 
    	public void onClick(View view)
    	{
    		if (!mGameOver)
    		{
    			if (mBoardButtons[location].isEnabled())
    			{
    				if (mIsSinglePlayer) 
    				{
    					setMove(TicTacToeGame.PLAYER_ONE, location);
 
	    				int winner = mGame.checkForWinner();
 
	    				if (winner == 0)
	    				{
	    					mInfoTextView.setText(R.string.turn_computer);
	    					int move = mGame.getComputerMove();
	    					setMove(TicTacToeGame.PLAYER_TWO, move);
	    					winner = mGame.checkForWinner();    					
	    				}
 
	    				if (winner == 0)
	    					mInfoTextView.setText(R.string.turn_human);
	    				else if (winner == 1)
	    				{
	    					mInfoTextView.setText(R.string.result_tie);
	    					mTieCounter++;
	    					mTieCount.setText(Integer.toString(mTieCounter));
	    					mGameOver = true;
	    				}
	    				else if (winner == 2)
	    				{
	    					mInfoTextView.setText(R.string.result_human_wins);
	    					mPlayerOneCounter++;
	    					mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
	    					mGameOver = true;
	    				}
	    				else
	    				{
	    					mInfoTextView.setText(R.string.result_android_wins);
	    					mPlayerTwoCounter++;
	    					mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
	    					mGameOver = true;
	    				}
    				}
    				else
    				{
    					if (mIsPlayerOneTurn)
    						setMove(TicTacToeGame.PLAYER_ONE, location);
    					else
    						setMove(TicTacToeGame.PLAYER_TWO, location);
 
	    				int winner = mGame.checkForWinner();
 
	    				if (winner == 0)
	    				{
	    					if (mIsPlayerOneTurn)
	    					{
	    						mInfoTextView.setText(R.string.turn_player_two);
	    						mIsPlayerOneTurn = false;
	    					}    					
	    					else
	    					{
	    						mInfoTextView.setText(R.string.turn_player_one);
	    						mIsPlayerOneTurn = true;
	    					}
	    				}
	    				else if (winner == 1)
	    				{
	    					mInfoTextView.setText(R.string.result_tie);
	    					mTieCounter++;
	    					mTieCount.setText(Integer.toString(mTieCounter));
	    					mGameOver = true;
	    				}
	    				else if (winner == 2)
	    				{
	    					mInfoTextView.setText(R.string.result_player_one_wins); 
	    					mPlayerOneCounter++;
	    					mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
	    					mGameOver = true;
	    					mIsPlayerOneTurn = false;
	    				}
	    				else
	    				{
	    					mInfoTextView.setText(R.string.result_player_two_wins); 
	    					mPlayerTwoCounter++;
	    					mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
	    					mGameOver = true;
	    					mIsPlayerOneTurn = true;
	    				}
    				}
    			}
    		}
    	}
    }
    
    private void setMove(char player, int location)
    {
    	mGame.setMove(player, location);
    	mBoardButtons[location].setEnabled(false);
       	//mBoardButtons[location].setText(String.valueOf(player));
    	if (player == TicTacToeGame.PLAYER_ONE)
    	{
    		//mBoardButtons[location].setTextColor(Color.GREEN);
    		mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.x));
    	}
    	else
    	{
    		//mBoardButtons[location].setTextColor(Color.RED);
    		mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.o));
    	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
