import { useEffect, useState, useRef } from 'react';
import { Client } from '@stomp/stompjs';

// Definiamo la struttura del messaggio per evitare l'errore del compilatore
interface ChatMessage {
  content: string;
}

export const WebSocketTest = () => {
  // Specifichiamo il tipo della lista di messaggi
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [status, setStatus] = useState<string>('Disconnesso');
  const stompClient = useRef<Client | null>(null);

  useEffect(() => {
    const client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      reconnectDelay: 5000,
      onConnect: () => {
        setStatus('Connesso');
        
        client.subscribe('/topic/greetings', (message) => {
          if (message.body) {
            const parsedMessage: ChatMessage = JSON.parse(message.body);
            setMessages((prev) => [...prev, parsedMessage]);
          }
        });
      },
      onDisconnect: () => {
        setStatus('Disconnesso');
      }
    });

    client.activate();
    stompClient.current = client;

    return () => {
      if (stompClient.current) {
        stompClient.current.deactivate();
      }
    };
  }, []);

  const sendMessage = () => {
    if (stompClient.current?.connected) {
      const payload: ChatMessage = { content: "Messaggio di test" };
      stompClient.current.publish({
        destination: '/app/hello',
        body: JSON.stringify(payload),
      });
    }
  };

  return (
    <div style={{ padding: '20px', border: '1px solid black' }}>
      <h3>Stato: {status}</h3>
      <button onClick={sendMessage} disabled={status !== 'Connesso'}>
        Invia Messaggio
      </button>
      <div style={{ marginTop: '10px' }}>
        <strong>Stream messaggi:</strong>
        {messages.map((m, i) => (
          <div key={i}>📩 {m.content}</div>
        ))}
      </div>
    </div>
  );
};