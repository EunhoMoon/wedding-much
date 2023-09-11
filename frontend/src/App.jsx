import './App.css';
import {Container, CssBaseline, Pagination} from "@mui/material";
import InputForm from "./component/InputForm";
import Header from "./component/Header";
import GiftTable from "./component/GiftTable";
import {Fragment, useCallback, useEffect, useState} from "react";
import axios from "axios";

const pageSize = 10;

function App() {
  const [presentPage, setPresentPage] = useState(1);
  const [gifts, setGifts] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [pages, setPages] = useState({
    pageCount: 1,
    total: 0,
    totalPrice: 0,
    presentPages: 1
  });
  const pagePerNum = pages.total - ((presentPage - 1) * pageSize);

  const getGiftsHandler = useCallback(async () => {
    setIsLoading(true);

    try {
      let response = await axios.get(`/api/gifts?page=${presentPage}&size=${pageSize}`);

      const {list, ...rest} = response.data

      setGifts(list);
      setPages(rest)
    } catch (error) {
      setError(error.message);
    }

    setIsLoading(false);
  }, [presentPage]);

  const pageChangeHandler = (event, value) => {
    setPresentPage(value)
  }

  const saveGiftHandler = async (gift) => {
    try {
      let response = await axios.post(`/api/gifts`, gift);
      if (response.status === 201) {
        getGiftsHandler();
      }
    } catch (e) {
      console.log(e)
    }
  }

  const deleteGiftHandler = async (token) => {
    try {
      let response = await axios.delete(`/api/gifts/${token}`);
      if (response.status === 200) {
        alert("정상적으로 삭제되었습니다.");
        getGiftsHandler();
      }
    } catch (e) {
      console.log(e)
    }
  }

  useEffect(() => {
    getGiftsHandler();
  }, [getGiftsHandler]);

  return (
    <div className="App">
      <CssBaseline/>
      <Header/>
      <Container style={{marginTop: 30, display: 'flex', justifyContent: 'center'}}>
        <InputForm onGiftSave={saveGiftHandler}/>
      </Container>
      {!error && (
        <Fragment>
          <Container style={{marginTop: 50, display: 'flex', justifyContent: 'center'}}>
            <div style={{width: '70%'}}>
              <div style={{display: "flex", justifyContent: 'space-between'}}>
                <div style={{display: "flex", justifyContent: 'flex-start'}}>
                  <p>총: {pages.total}명 /</p>
                  <p style={{marginLeft: 5}}>{pages.totalPrice.toLocaleString()}원</p>
                </div>
              </div>
              <GiftTable gifts={gifts} total={pagePerNum} onDeleteHandler={deleteGiftHandler}/>
            </div>
          </Container>
          <Container style={{marginTop: 30, display: 'flex', justifyContent: 'center'}}>
            <Pagination count={pages.pageCount} size="small" page={presentPage} onChange={pageChangeHandler}/>
          </Container>
        </Fragment>
      )}
      {error && (
        <Container style={{marginTop: 30, display: 'flex', justifyContent: 'center'}}>
          <p>데이터 로딩에 실패하였습니다.</p>
        </Container>
      )}
    </div>
  );
}

export default App;
