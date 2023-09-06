import './App.css';
import {Container, CssBaseline, Pagination} from "@mui/material";
import InputForm from "./component/InputForm";
import Header from "./component/Header";
import GiftTable from "./component/GiftTable";
import {useState} from "react";

function App() {
  const [page, setPage] = useState(1);

  function pageChangeHandler(event, value) {
    setPage(value)
  }

  return (
    <div className="App">
      <CssBaseline/>
      <Header/>
      <Container style={{marginTop: 30, display: 'flex', justifyContent: 'center'}}>
        <InputForm/>
      </Container>
      <Container style={{marginTop: 50, display: 'flex', justifyContent: 'center'}}>
        <GiftTable/>
      </Container>
      <Container style={{marginTop: 30, display: 'flex', justifyContent: 'center'}}>
        <Pagination count={10} size="small" page={page} onChange={pageChangeHandler}/>
      </Container>
    </div>
  );
}

export default App;
