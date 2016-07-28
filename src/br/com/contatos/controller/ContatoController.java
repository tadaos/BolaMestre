package br.com.contatos.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.com.contatos.helper.MySqlConnect;
import br.com.contatos.model.Contatos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ContatoController implements Initializable {

	@FXML TextField txtNome;
	@FXML TextField txtTel;
	@FXML Button btn, btndel, btnedit;
	@FXML ListView lstcontatos;

	private void preencherLista(){
		lstcontatos.getItems().clear();

		Connection con = MySqlConnect.ConectarDb();
		String sql = "select * from contact;";
		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Contatos c = new Contatos();
				c.setName(rs.getString("name"));
				c.setPhone(rs.getString("phone"));
				c.setId(rs.getInt("id"));

				lstcontatos.getItems().add(c);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML public void inserirContato() {
		Connection con = MySqlConnect.ConectarDb();
		String sql = "insert into contact (name, phone) values( ?, ?)";
		PreparedStatement parametros;

		try {

			parametros = con.prepareStatement(sql);
			parametros.setString(1, txtNome.getText());
			parametros.setString(2, txtTel.getText());

			parametros.executeUpdate();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		preencherLista();

	}

	@FXML public void deletarContato(){
		Connection con = MySqlConnect.ConectarDb();
		Contatos c1= new Contatos();

		c1=(Contatos) lstcontatos.getSelectionModel().getSelectedItem();

		String sql = "delete from contact where id=?;";
		PreparedStatement parametros;

		try {

			parametros = con.prepareStatement(sql);
			parametros.setLong(1, c1.getId());

			parametros.executeUpdate();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		preencherLista();
	}

	@FXML public void editarContato(){
		Connection con = MySqlConnect.ConectarDb();
		Contatos c2= new Contatos();

		c2=(Contatos) lstcontatos.getSelectionModel().getSelectedItem();

		String sql = "update contact set name=?, phone=? where id=?;";
		PreparedStatement parametros;

		try {

			parametros = con.prepareStatement(sql);
			parametros.setString(1, txtNome.getText());
			parametros.setString(2, txtTel.getText());
			parametros.setLong(3, c2.getId());

			parametros.executeUpdate();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		preencherLista();

	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		preencherLista();

	}

}