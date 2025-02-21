import java.util.ArrayList;

public class BlockList {
    public ArrayList<Block[]> blocks;
    public ArrayList<Character> blackList;

    public BlockList(){
        blocks = new ArrayList<Block[]>();
        blackList = new ArrayList<Character>();
    }

    public void setBlocks(Block block){
        blocks.add(new Block[]{
                block,
                block.rotateBlock().forcePositive(),
                block.rotateBlock().rotateBlock().forcePositive(),
                block.rotateBlock().rotateBlock().rotateBlock().forcePositive(),
                block.invertBlock().forcePositive(),
                block.invertBlock().rotateBlock().forcePositive(),
                block.invertBlock().rotateBlock().rotateBlock().forcePositive(),
                block.invertBlock().rotateBlock().rotateBlock().rotateBlock().forcePositive()
        });
    }
}
