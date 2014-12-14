package utils;

import android.os.Handler;

import com.dd.processbutton.ProcessButton;

import java.sql.SQLException;
import java.util.Random;

/**
 * Created by Investigación2 on 13/12/2014.
 */
public class ProgressGenerator {
    public interface OnCompleteListener {

        public void onComplete() throws SQLException;
    }

    private OnCompleteListener mListener;
    private int mProgress;

    public ProgressGenerator(OnCompleteListener listener) {
        mListener = listener;
    }

    public void start(final ProcessButton button) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                button.setProgress(mProgress);
                if (mProgress < 100) {
                    handler.postDelayed(this, generateDelay());
                } else {
                    try {
                        mListener.onComplete();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, generateDelay());
    }

    private Random random = new Random();

    private int generateDelay() {
        return random.nextInt(1000);
    }
}
