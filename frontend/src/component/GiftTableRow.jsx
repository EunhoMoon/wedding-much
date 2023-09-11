import React from 'react';
import {Button, TableCell, TableRow} from "@mui/material";

const GiftTableRow = props => {
  const {num, gift, onDeleteHandler} = props

  const deleteGiftHandler = () => {
    if (window.confirm("정말 삭제하시겠습니까?")) onDeleteHandler(gift.token);
  }

  return (
    <TableRow hover role="checkbox" tabIndex={-1}>
      <TableCell align="center">{num}</TableCell>
      <TableCell align="center">{gift.name}</TableCell>
      <TableCell align="center">{gift.memo}</TableCell>
      <TableCell align="right">{gift.price.toLocaleString()}</TableCell>
      <TableCell align="center">
        <Button onClick={deleteGiftHandler.bind()}>삭제</Button>
      </TableCell>
    </TableRow>
  );
};

export default GiftTableRow;