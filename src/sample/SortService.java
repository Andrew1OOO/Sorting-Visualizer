package sample;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class SortService extends Service<int[]> {

    private int[] array;

    public void sortArray(int[] array) {
        this.array = array;
        restart();  // restart() restarts the service regardless of its status
    }

    @Override
    public Task<int[]> createTask() {
        return new Task<int[]>() {
            @Override
            protected int[] call() throws Exception {
                // do your sorting and then return the resu

                return array;
            }
        };
    }
}
