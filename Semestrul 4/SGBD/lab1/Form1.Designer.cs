namespace Tema1
{
    partial class Form1
    {
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.Conectare = new System.Windows.Forms.Button();
            this.gridBAUTURI = new System.Windows.Forms.DataGridView();
            this.gridMENIU = new System.Windows.Forms.DataGridView();
            this.Adaugare = new System.Windows.Forms.Button();
            this.Stergere = new System.Windows.Forms.Button();
            this.Actualizare = new System.Windows.Forms.Button();
            this.ldenumire = new System.Windows.Forms.Label();
            this.lcantitate = new System.Windows.Forms.Label();
            this.lpret = new System.Windows.Forms.Label();
            this.DENUMIRE = new System.Windows.Forms.TextBox();
            this.CANTITATE = new System.Windows.Forms.TextBox();
            this.PRET = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.gridBAUTURI)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.gridMENIU)).BeginInit();
            this.SuspendLayout();
            // 
            // Conectare
            // 
            this.Conectare.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Conectare.Location = new System.Drawing.Point(712, 12);
            this.Conectare.Name = "Conectare";
            this.Conectare.Size = new System.Drawing.Size(463, 87);
            this.Conectare.TabIndex = 0;
            this.Conectare.Text = "Conectare";
            this.Conectare.UseVisualStyleBackColor = false;
            this.Conectare.Click += new System.EventHandler(this.Conectare_Click);
            // 
            // gridBAUTURI
            // 
            this.gridBAUTURI.BackgroundColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.gridBAUTURI.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.gridBAUTURI.GridColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.gridBAUTURI.Location = new System.Drawing.Point(995, 115);
            this.gridBAUTURI.Name = "gridBAUTURI";
            this.gridBAUTURI.RowHeadersWidth = 82;
            this.gridBAUTURI.RowTemplate.Height = 33;
            this.gridBAUTURI.Size = new System.Drawing.Size(905, 518);
            this.gridBAUTURI.TabIndex = 2;
            // 
            // gridMENIU
            // 
            this.gridMENIU.BackgroundColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.gridMENIU.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.gridMENIU.GridColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.gridMENIU.Location = new System.Drawing.Point(21, 115);
            this.gridMENIU.Name = "gridMENIU";
            this.gridMENIU.RowHeadersWidth = 82;
            this.gridMENIU.RowTemplate.Height = 33;
            this.gridMENIU.Size = new System.Drawing.Size(913, 518);
            this.gridMENIU.TabIndex = 3;
            // 
            // Adaugare
            // 
            this.Adaugare.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Adaugare.Location = new System.Drawing.Point(102, 686);
            this.Adaugare.Name = "Adaugare";
            this.Adaugare.Size = new System.Drawing.Size(463, 108);
            this.Adaugare.TabIndex = 4;
            this.Adaugare.Text = "Adaugare";
            this.Adaugare.UseVisualStyleBackColor = false;
            this.Adaugare.Click += new System.EventHandler(this.Adaugare_Click);
            // 
            // Stergere
            // 
            this.Stergere.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Stergere.Location = new System.Drawing.Point(712, 686);
            this.Stergere.Name = "Stergere";
            this.Stergere.Size = new System.Drawing.Size(463, 108);
            this.Stergere.TabIndex = 5;
            this.Stergere.Text = "Stergere";
            this.Stergere.UseVisualStyleBackColor = false;
            this.Stergere.Click += new System.EventHandler(this.Stergere_Click);
            // 
            // Actualizare
            // 
            this.Actualizare.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Actualizare.Location = new System.Drawing.Point(1307, 686);
            this.Actualizare.Name = "Actualizare";
            this.Actualizare.Size = new System.Drawing.Size(463, 108);
            this.Actualizare.TabIndex = 8;
            this.Actualizare.Text = "Actualizare";
            this.Actualizare.UseVisualStyleBackColor = false;
            this.Actualizare.Click += new System.EventHandler(this.Actualizare_Click);
            // 
            // ldenumire
            // 
            this.ldenumire.AutoSize = true;
            this.ldenumire.Location = new System.Drawing.Point(97, 845);
            this.ldenumire.Name = "ldenumire";
            this.ldenumire.Size = new System.Drawing.Size(126, 25);
            this.ldenumire.TabIndex = 9;
            this.ldenumire.Text = "Numele:";
            // 
            // lcantitate
            // 
            this.lcantitate.AutoSize = true;
            this.lcantitate.Location = new System.Drawing.Point(1302, 845);
            this.lcantitate.Name = "lcantitate";
            this.lcantitate.Size = new System.Drawing.Size(121, 25);
            this.lcantitate.TabIndex = 11;
            this.lcantitate.Text = "Cantitatea:";
            // 
            // lpret
            // 
            this.lpret.AutoSize = true;
            this.lpret.Location = new System.Drawing.Point(707, 845);
            this.lpret.Name = "lpret";
            this.lpret.Size = new System.Drawing.Size(131, 25);
            this.lpret.TabIndex = 12;
            this.lpret.Text = "Pretul:";
            // 
            // DENUMIRE
            // 
            this.DENUMIRE.BackColor = System.Drawing.SystemColors.HotTrack;
            this.DENUMIRE.ForeColor = System.Drawing.SystemColors.Window;
            this.DENUMIRE.Location = new System.Drawing.Point(239, 842);
            this.DENUMIRE.Name = "denumire";
            this.DENUMIRE.Size = new System.Drawing.Size(326, 31);
            this.DENUMIRE.TabIndex = 15;
            // 
            // CANTITATE
            // 
            this.CANTITATE.BackColor = System.Drawing.SystemColors.HotTrack;
            this.CANTITATE.ForeColor = System.Drawing.SystemColors.Window;
            // Am schimbat locația pentru a fi lângă eticheta "Cantitatea:"
            this.CANTITATE.Location = new System.Drawing.Point(1451, 845);
            this.CANTITATE.Name = "cantitate";
            this.CANTITATE.Size = new System.Drawing.Size(321, 31);
            this.CANTITATE.TabIndex = 14;
            // 
            // PRET
            // 
            this.PRET.BackColor = System.Drawing.SystemColors.HotTrack;
            this.PRET.ForeColor = System.Drawing.SystemColors.Window;
            // Am schimbat locația pentru a fi lângă eticheta "Pretul:"
            this.PRET.Location = new System.Drawing.Point(854, 842);
            this.PRET.Name = "pret";
            this.PRET.Size = new System.Drawing.Size(319, 31);
            this.PRET.TabIndex = 13;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 25F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.GhostWhite;
            this.ClientSize = new System.Drawing.Size(1958, 945);
            this.Controls.Add(this.DENUMIRE);
            this.Controls.Add(this.CANTITATE);
            this.Controls.Add(this.PRET);
            this.Controls.Add(this.lpret);
            this.Controls.Add(this.lcantitate);
            this.Controls.Add(this.ldenumire);
            this.Controls.Add(this.Actualizare);
            this.Controls.Add(this.Stergere);
            this.Controls.Add(this.Adaugare);
            this.Controls.Add(this.gridMENIU);
            this.Controls.Add(this.gridBAUTURI);
            this.Controls.Add(this.Conectare);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.gridBAUTURI)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.gridMENIU)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button Conectare;
        private System.Windows.Forms.DataGridView gridMENIU;
        private System.Windows.Forms.DataGridView gridBAUTURI;
        private System.Windows.Forms.Button Adaugare;
        private System.Windows.Forms.Button Stergere;
        private System.Windows.Forms.Button Actualizare;
        private System.Windows.Forms.Label ldenumire;
        private System.Windows.Forms.Label lcantitate;
        private System.Windows.Forms.Label lpret;
        private System.Windows.Forms.TextBox DENUMIRE;
        private System.Windows.Forms.TextBox CANTITATE;
        private System.Windows.Forms.TextBox PRET;
    }
}
