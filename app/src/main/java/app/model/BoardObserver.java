package app.model;

public interface BoardObserver {
    void onBoardChanged();
    void onBlockLocked();
    void onLineCleared();
    void onLevelUp(int newLevel);

}

