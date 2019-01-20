# ticketchain

Proof of concept to share tickets over the iota network. The tickets will be created and managed by the `frontend`, tranfered by a `backend` and consumed by a `android app`.

## build (every part)

```
git clone https://github.com/hannesN/ticketchain.git

cd ticketchain/
```

## components

### Backend 

The backend is a `http` endpoint. 

- "/GET /tickets/:event/:date"
- "/GET /tickets"
- "/POST /tickets --data JSON"

### Vue Frontend 

Get access the backend to create, manage and transfer Tickets.

### Android App

The Android is a Iota - Wallet to hold the ident Ticket. 



