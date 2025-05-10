using Newtonsoft.Json;

namespace networkingnet.RpcProtocol
{
    public static class JsonConfig
    {
        public static readonly JsonSerializerSettings Settings = new JsonSerializerSettings
        {
            TypeNameHandling = TypeNameHandling.Auto
        };
    }
}
