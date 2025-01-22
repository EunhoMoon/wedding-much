import React, {Fragment, useCallback, useEffect, useState} from 'react';
import classes from "../../css/App.module.css";
import GiftInputForm from "./GiftInputForm";
import GiftHeader from "./GiftHeader";
import GiftTable from "./GiftTable";
import {CircularProgress, Pagination} from "@mui/material";
import axios from "axios";

const pageSize = 10;

const Gift = () => {
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
  const [sort, setSort] = useState({
    by: 'ID',
    direction: 'DESC'
  });
  const pagePerNum = pages.total - ((presentPage - 1) * pageSize);

  const getGiftsHandler = useCallback(async () => {
    setIsLoading(true);
    console.log(sort)

    try {
      let response = await axios
        .get(`/api/gifts?page=${presentPage}&size=${pageSize}&sort=${sort.by}&direction=${sort.direction}`);

      const {list, ...rest} = response.data

      setGifts(list);
      setPages(rest)
    } catch (error) {
      setError(error.message);
    }

    setIsLoading(false)
  }, [presentPage, sort]);

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

  const updateGiftHandler = async (newGift) => {
    try {
      const response = await axios.put(`/api/gifts`, newGift);
      if (response.status === 200) {
        await getGiftsHandler();
      }
    } catch (e) {
      alert("수정에 실패하였습니다.");
      console.log(e)
    }
  }

  const deleteGiftHandler = async (token) => {
    try {
      let response = await axios.delete(`/api/gifts/${token}`);
      if (response.status === 200) {
        await getGiftsHandler();
        alert("정상적으로 삭제되었습니다.");
      }
    } catch (e) {
      alert("삭제에 실패하였습니다.");
      console.log(e)
    }
  }

  const sortingGiftsHandler = async (by, direction) => {
    console.log({by, direction})
    setSort({by, direction});
    setPresentPage(1);
    getGiftsHandler();
  }

  useEffect(() => {
    getGiftsHandler();
  }, [getGiftsHandler]);
  return (
    <Fragment>
      <div className={classes.wrapper}>
        <GiftInputForm onGiftSave={saveGiftHandler}/>
      </div>
      {!error && (
        <Fragment>
          <div className={classes.tableOuter}>
            {!isLoading && (
              <div className={classes.tableInner}>
                <GiftHeader total={pages.total} totalPrice={pages.totalPrice}/>
                <GiftTable gifts={gifts} total={pagePerNum} sort={sort}
                           onDelete={deleteGiftHandler}
                           onUpdate={updateGiftHandler}
                           onSorting={sortingGiftsHandler}/>
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
    </Fragment>
  );
};

export default Gift;