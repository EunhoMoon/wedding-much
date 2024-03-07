import React, {useRef, useState} from 'react';
import {Button, TableCell, TableRow} from "@mui/material";

const GiftTableRow = props => {
  const {num, gift, onDelete} = props;
  const [onUpdate, setOnUpdate] = useState(false);

  const nameRef = useRef();
  const memoRef = useRef();
  const priceRef = useRef();

  const updateGiftHandler = () => {
    setOnUpdate(true)
  }

  const updateGiftConfirmHandler = () => {
    const nameValue = nameRef.current.value
    const memoValue = memoRef.current.value
    const priceValue = priceRef.current.value

    const updateGift = {
      token: gift.token,
      name: nameValue,
      memo: memoValue,
      price: Number(priceValue)
    }

    // onUpdate(updateGift)

    setOnUpdate(false)
  }

  const deleteGiftHandler = () => {
    if (window.confirm("정말 삭제하시겠습니까?")) {
      onDelete(gift.token)
    }
  }

  return (
    <TableRow hover role="checkbox" tabIndex={-1}>
      <TableCell align="center">{num}</TableCell>
      <TableCell align="center">
        {onUpdate ? <input type="text" ref={nameRef} defaultValue={gift.name}/> : gift.name}
      </TableCell>
      <TableCell align="center">
        {onUpdate ? <input type="text" ref={memoRef} defaultValue={gift.memo}/> : gift.memo}
      </TableCell>
      <TableCell align="right">
        {onUpdate ? <input type="number" ref={priceRef} defaultValue={gift.price}/> : gift.price.toLocaleString()}
      </TableCell>
      <TableCell align="center">
        {onUpdate ? <Button onClick={updateGiftConfirmHandler.bind()}>확인</Button> :
          <Button onClick={updateGiftHandler.bind()}>수정</Button>}
      </TableCell>
      <TableCell align="center">
        <Button onClick={deleteGiftHandler.bind()}>삭제</Button>
      </TableCell>
    </TableRow>
  );
};

export default GiftTableRow;