export default function Layout({ children }) {
    return (
      <>
        <nav className="bg-blue-600 p-4 text-white">My App</nav>
        <main className="p-6">{children}</main>
        <footer className="bg-gray-200 text-center p-4">©2025</footer>
      </>
    );
  }
  