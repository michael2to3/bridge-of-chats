package bridge.chats;

import java.util.ArrayList;
import java.util.List;

public class LinkerManager {
  private List<Thread> linkers = new ArrayList<>();

  public LinkerManager() {}

  public void startLinker(Linker linker) {
    Thread linkerThread = new Thread(new LinkerRunnable(linker));
    linkerThread.start();
    linkers.add(linkerThread);
  }

  public void stopAllLinkers() {
    for (Thread linker : linkers) {
      linker.interrupt();
    }
    linkers.clear();
  }

  private class LinkerRunnable implements Runnable {
    private Linker linker;

    public LinkerRunnable(Linker linker) {
      this.linker = linker;
    }

    @Override
    public void run() {
      linker.start();
    }
  }
}
