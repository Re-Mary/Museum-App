import { useState } from 'react'
import type { FormEvent } from 'react'
import './App.css'

function App() {
  const [username, setUsername] = useState('admin')
  const [password, setPassword] = useState('admin123')
  const [token, setToken] = useState<string | null>(null)
  const [message, setMessage] = useState('Not authenticated')
  const [error, setError] = useState<string | null>(null)

  const handleLogin = async (event: FormEvent) => {
    event.preventDefault()
    setError(null)

    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      })

      if (!response.ok) {
        throw new Error('Login failed. Check credentials.')
      }

      const data: { token: string } = await response.json()
      setToken(data.token)
      setMessage('Login successful')
    } catch (loginError) {
      setToken(null)
      setMessage('Not authenticated')
      setError(loginError instanceof Error ? loginError.message : 'Unknown error')
    }
  }

  const loadSecureData = async () => {
    if (!token) {
      setError('Token is missing. Login first.')
      return
    }

    setError(null)
    const response = await fetch('/api/secure/me', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })

    if (!response.ok) {
      setError('Failed to load protected endpoint.')
      return
    }

    const text = await response.text()
    setMessage(text)
  }

  return (
    <main className="app-shell">
      <h1>Museum App Foundation</h1>
      <p>Frontend: React + Vite. Backend: Spring Boot + JWT.</p>

      <form className="login-card" onSubmit={handleLogin}>
        <label>
          Username
          <input value={username} onChange={(e) => setUsername(e.target.value)} />
        </label>
        <label>
          Password
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>
        <button type="submit">Login</button>
      </form>

      <section className="status-card">
        <p>{message}</p>
        <button type="button" onClick={loadSecureData}>
          Call protected endpoint
        </button>
      </section>

      {error ? <p className="error">{error}</p> : null}
      {token ? <p className="token">JWT: {token.slice(0, 40)}...</p> : null}
    </main>
  )
}

export default App
