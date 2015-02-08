/**
 * @author Igor Baiborodine
 */
function createCharactersList() {

    var javaImporter = new JavaImporter(
        java.util,
        javax.swing,
        java.awt
    );

    var frame;

    with (javaImporter) {
        frame = new JFrame();
        frame.setTitle("Futurama Characters");
        frame.setSize(300, 200);
        frame.setBackground(Color.gray);

        var content = new JPanel();
        content.setLayout(new BorderLayout());
        frame.getContentPane().add(content);

        var arrayOfStrings = Java.type("java.lang.String[]");
        var data = new arrayOfStrings(4);
        data[0] = "Bender Rodriguez";
        data[1] = "Philip Fry";
        data[2] = "Turanga Leela";
        data[3] = "Hubert Farnsworth";

        var list = new JList(data);
        content.add(list, BorderLayout.CENTER);
    }
    return frame;
}
