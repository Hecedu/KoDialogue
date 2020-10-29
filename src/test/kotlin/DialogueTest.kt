package DialogueProject
import kotlin.test.assertEquals
import org.junit.Test as test


class TestSource() {
    @test fun f() {
        val initNode = DialogueNode("test")
        val subNode1 = DialogueNode ("abc")
        initNode.appendDialogue(subNode1, "directive")
        val subNode2 = DialogueNode ("123")
        initNode.appendDialogue(subNode2, "directive2")
        val dialogue = Dialogue(initNode)


    }
}