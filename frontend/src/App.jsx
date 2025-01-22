import {CssBaseline} from "@mui/material";
import Header from "./component/Header";
import Gift from "./component/Gift/Gift";

function App() {
  return (
    <div className="App">
      <CssBaseline/>
      <Header/>
      <Gift/>
    </div>
  );
}

export default App;
