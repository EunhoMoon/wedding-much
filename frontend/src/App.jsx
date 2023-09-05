import './App.css';
import {Button, TextField} from "@mui/material";

function App() {
  return (
    <div className="App">
      <div style={{display: 'flex', justifyContent: 'center'}}>
        <div style={{display: 'flex', justifyContent: 'flex-start'}}>
          <TextField style={{margin: '0 5px'}} name="name" label="이름" variant="outlined" size="small" color="success"/>
          <TextField style={{margin: '0 5px'}}  name="price" label="금액" variant="outlined" size="small" color="success" type="number"/>
          <TextField style={{margin: '0 5px'}}  name="memo" label="비고" variant="outlined" size="small" color="success"/>
          <Button variant="outlined" style={{margin: '0 5px'}} color="success" >저장</Button>
        </div>
      </div>
    </div>
  );
}

export default App;
