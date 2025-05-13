using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Json;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace CazRestClient
{
    public class Caz
    {
        public int Id { get; set; }
        [JsonPropertyName("nume_caz")]
        public string NumeCaz { get; set; }
        public string Descriere { get; set; }

        public override string ToString() =>
            $"Caz {{ Id = {Id}, NumeCaz = \"{NumeCaz}\", Descriere = \"{Descriere}\" }}";
    }

    class Program
    {
        private static readonly string BaseUrl = "http://localhost:8080/api/cazuri";
        private static readonly HttpClient Client = new HttpClient();

        static async Task Main(string[] args)
        {
            try
            {
                var toCreate = new Caz {
                    NumeCaz = "Ajutor urgente",
                    Descriere = "Colecta pentru victimele inundatiilor"
                };
                var createResp = await Client.PostAsJsonAsync(BaseUrl, toCreate);
                createResp.EnsureSuccessStatusCode();
                int newId = await createResp.Content.ReadFromJsonAsync<int>();
                Console.WriteLine($"Created Caz with Id = {newId}");

                var fetched = await Client.GetFromJsonAsync<Caz>($"{BaseUrl}/{newId}");
                Console.WriteLine($"Fetched: {fetched}");

                fetched.Descriere = "Actualizare descriere: foarte urgent";
                var updateResp = await Client.PutAsJsonAsync($"{BaseUrl}/{newId}", fetched);
                updateResp.EnsureSuccessStatusCode();
                Console.WriteLine("Updated successfully.");

                var updated = await Client.GetFromJsonAsync<Caz>($"{BaseUrl}/{newId}");
                Console.WriteLine($"Updated fetched: {updated}");

                var all = await Client.GetFromJsonAsync<List<Caz>>(BaseUrl);
                Console.WriteLine("All cazuri:");
                all.ForEach(c => Console.WriteLine("  " + c));

                var deleteResp = await Client.DeleteAsync($"{BaseUrl}/{newId}");
                if (deleteResp.IsSuccessStatusCode)
                    Console.WriteLine($"Deleted Caz id={newId}");
                else
                    Console.WriteLine($"Delete failed: {deleteResp.StatusCode}");
            }
            catch (HttpRequestException e)
            {
                Console.Error.WriteLine("HTTP error: " + e.Message);
            }
            catch (NotSupportedException) 
            {
                Console.Error.WriteLine("The content type is not supported.");
            }
            catch (JsonException) 
            {
                Console.Error.WriteLine("Invalid JSON.");
            }
        }
    }
}
