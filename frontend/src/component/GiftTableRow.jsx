import React from 'react';
import {Button, TableCell, TableRow} from "@mui/material";

const GiftTableRow = props => {
  const {num, gift} = props
  return (
    <TableRow hover role="checkbox" tabIndex={-1} key={gift.token}>
      <TableCell align="center">{num}</TableCell>
      <TableCell align="center">{gift.name}</TableCell>
      <TableCell align="center">{gift.memo}</TableCell>
      <TableCell align="right">{gift.price.toLocaleString()}</TableCell>
      <TableCell align="center">
        <Button>삭제</Button>
      </TableCell>
    </TableRow>
  );
};

export default GiftTableRow;