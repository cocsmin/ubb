using csharp_avalonia.domain;
using csharp_avalonia.repository;

namespace csharp_avalonia.service;

public class DonatieService
    {
        private readonly DonatieRepo0 donatieRepo;

        public DonatieService(DonatieRepo0 donatieRepo1)
        {
            donatieRepo = donatieRepo1;
        }

        public void SaveDonatie(Donatie donatie)
        {
            donatieRepo.save(donatie);
        }

        public int GetSumaDonatiiPentruCaz(int cazId)
        {
            return donatieRepo.GetSumaDonatiiPentruCaz(cazId);
        }
    }

