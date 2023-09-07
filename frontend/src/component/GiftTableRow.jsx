import React from 'react';
import {TableCell, TableRow} from "@mui/material";

const GiftTableRow = props => {
  const {num, gift} = props
  return (
    <TableRow hover role="checkbox" tabIndex={-1} key={gift.token}>
      <TableCell align="center">{num}</TableCell>
      <TableCell align="center">{gift.name}</TableCell>
      <TableCell align="center">{gift.memo}</TableCell>
      <TableCell align="right" style={{paddingRight: '10%'}}>{gift.price.toLocaleString()}</TableCell>
    </TableRow>
  );
};

export default GiftTableRow;