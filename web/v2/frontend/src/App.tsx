import { AuthProvider } from "./context/AuthContext"
import Router from "./routes/Router"

export const App = () => {
  return (
    <AuthProvider>
      <Router/>
    </AuthProvider>
  )
}
