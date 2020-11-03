package DialogueProject
import kotlin.test.assertEquals
import org.junit.Test as test


class TestSource {
    @test fun f() {

        val rootNode = DialogueNode("test1")

            val choiceNode = DialogueNodeChoice("testChoice")
            rootNode.appendDialogueChoice(choiceNode, "choice leads to node")
            val fakeNode = DialogueNodeFakeChoice("testFake")
            rootNode.setDialogueFakeChoice(fakeNode, arrayOf("all","choices","lead","to","fake"))
        val dialogue = Dialogue(rootNode)

        assertEquals(dialogue.rootDialogue.nodeDialogue, "test")
        assertEquals(dialogue.rootDialogue.childrenDialogueChoiceList.first().nodeDialogue, "testChoice")
        assertEquals(dialogue.rootDialogue.fakeChoice!!.nodeDialogue, "testFake")
    }
}