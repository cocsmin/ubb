using System;
using System.Data;
using System.Data.SqlClient;
using System.Text.RegularExpressions;
using System.Windows.Forms;

namespace Tema1
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=192.168.1.148,1433;Initial Catalog=GLOVO;User ID=sa;Password=12345OHdf%e;");
        SqlDataAdapter daO = new SqlDataAdapter();
        SqlDataAdapter daT = new SqlDataAdapter();
        DataSet dsO = new DataSet();
        DataSet dsT = new DataSet();

        public Form1()
        {
            InitializeComponent();
            gridMENIU.SelectionChanged += gridMENIU_SelectionChanged;
            Stergere.Click += Stergere_Click;
            Actualizare.Click += Actualizare_Click;
            Adaugare.Click += Adaugare_Click;
        }

        private void Conectare_Click(object sender, EventArgs e)
        {
            daT.SelectCommand = new SqlCommand("SELECT * FROM MENIU", cs);
            dsT.Clear();
            daT.Fill(dsT);
            gridMENIU.DataSource = dsT.Tables[0];
        }

        private void gridMENIU_SelectionChanged(object sender, EventArgs e)
        {
            if (gridMENIU.SelectedRows.Count > 0)
            {
                int id_MENIU = Convert.ToInt32(gridMENIU.SelectedRows[0].Cells["ID_MENIU"].Value);
                LoadBAUTURI(id_MENIU);
            }
        }

        private void LoadBAUTURI(int id_MENIU)
        {
            try
            {
                string query = "SELECT * FROM BAUTURI WHERE ID_MENIU = @id_MENIU";
                daO.SelectCommand = new SqlCommand(query, cs);
                daO.SelectCommand.Parameters.Clear();
                daO.SelectCommand.Parameters.AddWithValue("@id_MENIU", id_MENIU);

                dsO.Clear();
                gridBAUTURI.DataSource = null;
                daO.Fill(dsO);
                gridBAUTURI.DataSource = dsO.Tables[0];
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: " + ex.Message);
            }
        }

        private void Adaugare_Click(object sender, EventArgs e)
        {
            try
            {
                if (!ValidareInput()) return;

                int id_MENIU = Convert.ToInt32(gridMENIU.SelectedRows[0].Cells["id_MENIU"].Value);

                daO.InsertCommand = new SqlCommand("INSERT INTO BAUTURI (ID_MENIU, DENUMIRE, CANTITATE, PRET) VALUES (@t, @n, @s, @l)", cs);
                daO.InsertCommand.Parameters.Add("@t", SqlDbType.Int).Value = id_MENIU;
                daO.InsertCommand.Parameters.Add("@n", SqlDbType.VarChar).Value = DENUMIRE.Text;
                daO.InsertCommand.Parameters.Add("@s", SqlDbType.Int).Value = int.Parse(CANTITATE.Text);
                daO.InsertCommand.Parameters.Add("@l", SqlDbType.Int).Value = int.Parse(PRET.Text);

                cs.Open();
                daO.InsertCommand.ExecuteNonQuery();
                cs.Close();

                MessageBox.Show("Adaugarea s-a realizat cu succes!");
                LoadBAUTURI(id_MENIU);
                GolireTextBox();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: " + ex.Message);
                cs.Close();
            }
        }

        private void Stergere_Click(object sender, EventArgs e)
        {
            try
            {
                if (gridBAUTURI.SelectedRows.Count == 0)
                {
                    MessageBox.Show("Selecteaza o bautura pentru a o sterge!");
                    return;
                }

                int id_BAUTURA = Convert.ToInt32(dsO.Tables[0].Rows[gridBAUTURI.CurrentCell.RowIndex][0]);

                daO.DeleteCommand = new SqlCommand("DELETE FROM BAUTURI WHERE ID_BAUTURA = @id_BAUTURA", cs);
                daO.DeleteCommand.Parameters.Add("@id_BAUTURA", SqlDbType.Int).Value = id_BAUTURA;

                cs.Open();
                daO.DeleteCommand.ExecuteNonQuery();
                cs.Close();

                MessageBox.Show("Stergerea s-a realizat cu succes!");
                int id_MENIU = Convert.ToInt32(gridMENIU.SelectedRows[0].Cells["ID_MENIU"].Value);
                LoadBAUTURI(id_MENIU);
                GolireTextBox();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: " + ex.Message);
                cs.Close();
            }
        }

        private void Actualizare_Click(object sender, EventArgs e)
        {
            try
            {
                if (gridBAUTURI.SelectedRows.Count == 0)
                {
                    MessageBox.Show("Selecteaza o bautura pentru a o actualiza!");
                    return;
                }

                if (!ValidareInput()) return;

                int id_BAUTURA = Convert.ToInt32(dsO.Tables[0].Rows[gridBAUTURI.CurrentCell.RowIndex][0]);

                daO.UpdateCommand = new SqlCommand("UPDATE BAUTURI SET DENUMIRE = @n, CANTITATE = @s, PRET = @l WHERE ID_BAUTURA = @id", cs);
                daO.UpdateCommand.Parameters.Add("@n", SqlDbType.VarChar).Value = DENUMIRE.Text;
                daO.UpdateCommand.Parameters.Add("@s", SqlDbType.Int).Value = int.Parse(CANTITATE.Text);
                daO.UpdateCommand.Parameters.Add("@l", SqlDbType.Int).Value = int.Parse(PRET.Text);
                daO.UpdateCommand.Parameters.Add("@id", SqlDbType.Int).Value = id_BAUTURA;

                cs.Open();
                daO.UpdateCommand.ExecuteNonQuery();
                cs.Close();

                MessageBox.Show("Actualizarea s-a realizat cu succes!");
                int id_MENIU = Convert.ToInt32(gridMENIU.SelectedRows[0].Cells["ID_MENIU"].Value);
                LoadBAUTURI(id_MENIU);
                GolireTextBox();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: " + ex.Message);
                cs.Close();
            }
        }

        private bool ValidareInput()
        {
            if (string.IsNullOrWhiteSpace(DENUMIRE.Text) || string.IsNullOrWhiteSpace(CANTITATE.Text) || string.IsNullOrWhiteSpace(PRET.Text))
            {
                MessageBox.Show("Toate campurile trebuie completate!", "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return false;
            }

            if (!Regex.IsMatch(DENUMIRE.Text, @"^[a-zA-Z\s]+$"))
            {
                MessageBox.Show("Numele bauturii trebuie sa contina doar litere!", "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return false;
            }

            if (!int.TryParse(CANTITATE.Text, out int nr) || !int.TryParse(PRET.Text, out int nr2) || nr <= 0 || nr2 <= 0)
            {
                MessageBox.Show("Pretul bauturii sau cantitatea acesteia trebuie sa fie un numar pozitiv!", "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return false;
            }
            if (ExistaBAUTURA(DENUMIRE.Text))
            {
                MessageBox.Show("Bautura exista deja in baza de date!", "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return false;
            }

            return true;
        }

        private bool ExistaBAUTURA(string numeBAUTURA)
        {
            SqlCommand cmd = new SqlCommand("SELECT COUNT(*) FROM BAUTURI WHERE DENUMIRE = @nume", cs);
            cmd.Parameters.AddWithValue("@nume", numeBAUTURA);
            cs.Open();
            int count = (int)cmd.ExecuteScalar();
            cs.Close();
            return count > 0;
        }

        public void GolireTextBox()
        {
            DENUMIRE.Text = "";
            CANTITATE.Text = "";
            PRET.Text = "";
        }
    }
}
