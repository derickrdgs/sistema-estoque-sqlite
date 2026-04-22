import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = Conexao.conectar();
        Scanner sc = new Scanner(System.in);

        if (conn == null) {
            System.out.println("Falha na conexão com o banco.");
            return;
        }

        try {
            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS produtos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "quantidade INTEGER," +
                    "preco REAL)");

            int opcao;

            do {
                System.out.println("\nESTOQUE COM BANCO DE DADOS");
                System.out.println("1 - Inserir produto");
                System.out.println("2 - Listar produtos");
                System.out.println("0 - Sair");
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();

                        System.out.print("Quantidade: ");
                        int qtd = sc.nextInt();

                        System.out.print("Preço: ");
                        double preco = sc.nextDouble();

                        PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO produtos (nome, quantidade, preco) VALUES (?, ?, ?)"
                        );

                        ps.setString(1, nome);
                        ps.setInt(2, qtd);
                        ps.setDouble(3, preco);
                        ps.executeUpdate();

                        System.out.println("Produto inserido.");
                        break;

                    case 2:
                        ResultSet rs = stmt.executeQuery("SELECT * FROM produtos");

                        while (rs.next()) {
                            System.out.println("ID: " + rs.getInt("id"));
                            System.out.println("Nome: " + rs.getString("nome"));
                            System.out.println("Quantidade: " + rs.getInt("quantidade"));
                            System.out.println("Preço: R$" + rs.getDouble("preco"));
                            System.out.println("--------------------");
                        }
                        break;
                }

            } while (opcao != 0);

            conn.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        sc.close();
    }
}
