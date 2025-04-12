using System;
using System.Collections.ObjectModel;
using System.Reactive;
using csharp_avalonia.domain;
using csharp_avalonia.service;
using ReactiveUI;

namespace csharp_avalonia.ViewModels;

public class DonationViewModel : ReactiveObject
    {
        private readonly DonatieService donatieService;
        private readonly DonatorService donatorService;
        private readonly CazService cazService;

        public ObservableCollection<Caz> Cazuri { get; } = new ObservableCollection<Caz>();
        private Caz selectedCaz;
        public Caz SelectedCaz
        {
            get => selectedCaz;
            set => this.RaiseAndSetIfChanged(ref selectedCaz, value);
        }

        private string donationAmount;
        public string DonationAmount
        {
            get => donationAmount;
            set => this.RaiseAndSetIfChanged(ref donationAmount, value);
        }

        private string donorName;
        public string DonorName
        {
            get => donorName;
            set => this.RaiseAndSetIfChanged(ref donorName, value);
        }

        private string donorAddress;
        public string DonorAddress
        {
            get => donorAddress;
            set => this.RaiseAndSetIfChanged(ref donorAddress, value);
        }

        private string donorPhone;
        public string DonorPhone
        {
            get => donorPhone;
            set => this.RaiseAndSetIfChanged(ref donorPhone, value);
        }

        private string donorSearch;
        public string DonorSearch
        {
            get => donorSearch;
            set => this.RaiseAndSetIfChanged(ref donorSearch, value);
        }

        public ObservableCollection<Donator> Donors { get; } = new ObservableCollection<Donator>();

        public ReactiveCommand<Unit, Unit> SearchDonorCommand { get; }
        public ReactiveCommand<Unit, Unit> SaveDonationCommand { get; }

        public DonationViewModel(DonatieService donatieService1, DonatorService donatorService1, CazService cazService1)
        {
            donatieService = donatieService1;
            donatorService = donatorService1;
            cazService = cazService1;

            var cazuriList = cazService.FindAll();
            foreach (var caz in cazuriList)
                Cazuri.Add(caz);

            SearchDonorCommand = ReactiveCommand.Create(SearchDonors);
            SaveDonationCommand = ReactiveCommand.Create(SaveDonation);
        }

        private void SearchDonors()
        {
            Donors.Clear();
            var results = donatorService.SearchByName(DonorSearch);
            foreach (var donor in results)
                Donors.Add(donor);
        }

        private void SaveDonation()
        {
            try
            {
                int amount = int.Parse(DonationAmount);
                Donator donor = donatorService.FindByFullName(DonorName);
                if (donor == null)
                {
                    donor = new Donator(DonorName, DonorAddress, DonorPhone);
                    donor = donatorService.save(donor);
                }
                var donation = new Donatie(donor, SelectedCaz, DateTime.Now, amount);
                donatieService.SaveDonatie(donation);
                DonorName = null;
                DonorAddress = null;
                DonorPhone = null;
                DonorSearch = null;
                DonationAmount = null;
                SelectedCaz = null;
                Console.WriteLine("Donation saved successfully!");
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error saving donation: " + ex.Message);
            }
        }
    }