import { useEffect, useState } from "react";
import { Routes, Route, Navigate, useLocation } from "react-router-dom";
import Login from "./pages/Login";
import Cazuri from "./pages/Cazuri";
import Layout from "./components/Layout";

function App() {
  const [token, setToken] = useState(localStorage.getItem("jwt"));
  const location = useLocation();

  useEffect(() => {
    const handleStorage = () => {
      const jwt = localStorage.getItem("jwt");
      setToken(jwt);
    };
  
    window.addEventListener("storage", handleStorage);
    return () => window.removeEventListener("storage", handleStorage);
  }, []);

  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route
        path="/cazuri"
        element={token ? 
        (<Layout>
        <Cazuri /> 
        </Layout> ): (<Navigate to="/login" />)}
      />
      <Route path="/" element={<Navigate to={token ? "/cazuri" : "/login"} />} />
      <Route path="*" element={<Navigate to={token ? "/cazuri" : "/login"} />} />
    </Routes>
  );
}

export default App;
