import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './App.css'
// import needed for localization
import './i18n';
import App from './App.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
