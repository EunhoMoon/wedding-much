import {CircularProgress, CssBaseline, Pagination} from "@mui/material";
import InputForm from "./component/InputForm";
import Header from "./component/Header";
import GiftTable from "./component/GiftTable";
import {Fragment, useCallback, useEffect, useState} from "react";
import axios from "axios";
import GiftHeader from "./component/GiftHeader";
import classes from './css/App.module.css';

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

    setIsLoading(false)
  }, [presentPage]);

  const pageChangeHandler = (event, value) => {
    setPresentPage(value)
  }

  const saveGiftHandler = async (gift) => {
    try {
      let response = await axios.post(`/api/gifts`, gift);
      if (response.status === 201) {
        await getGiftsHandler();
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
      <div className={classes.wrapper}>
        <InputForm onGiftSave={saveGiftHandler}/>
      </div>
      {!error && (
        <Fragment>
          <div className={classes.tableOuter}>
            {!isLoading && (
              <div className={classes.tableInner}>
                <GiftHeader total={pages.total} totalPrice={pages.totalPrice}/>
                <GiftTable gifts={gifts} total={pagePerNum} onDeleteHandler={deleteGiftHandler}/>
              </div>
            )}
            {isLoading && <CircularProgress/>}
          </div>
          <div className={classes.wrapper}>
            <Pagination count={pages.pageCount} size="small" page={presentPage} onChange={pageChangeHandler}/>
          </div>
        </Fragment>
      )}
      {error && <div className={classes.wrapper}><p>데이터 로딩에 실패하였습니다.</p></div>}
    </div>
  );
}

export default App;
